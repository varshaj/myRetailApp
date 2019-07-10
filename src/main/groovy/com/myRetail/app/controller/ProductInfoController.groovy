package com.myRetail.app.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.myRetail.app.domain.CurrentPrice
import com.myRetail.app.domain.Product
import com.myRetail.app.exception.InvalidPriceUpdateException
import com.myRetail.app.exception.PreconditionFailedException
import com.myRetail.app.service.ProductInfoService
import com.myRetail.app.service.ProductPriceService
import com.myRetail.app.service.RedSkyService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping("/products")
@Api(value = "MyRetail API", description = "Operations for getting product details")
@Slf4j
@CompileStatic
class ProductInfoController {
    @Autowired
    RedSkyService redSkyService

    @Autowired
    ProductInfoService productInfoService

    @Autowired
    ProductPriceService productPriceService

    @Autowired
    ObjectMapper objectMapper

    @ApiOperation(value = "Get product details by productId", response = Product.class, tags = ["Get Product Information"])
    @GetMapping(value = ['{productId}'], produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProduct(@PathVariable(value="productId") Long productId) {
        new Product(id: productId, name: redSkyService.getProductName(productId),
                current_price: productPriceService.getProductPrice(productId) as CurrentPrice)
    }

    @Cacheable(cacheNames = "productInfo", key = "#productId")
    @ApiOperation(value = "Get product details by productId async", response = Product.class, tags = ["Get Product Information asyncronously"])
    @GetMapping(value = ['{productId}/async'], produces = MediaType.APPLICATION_JSON_VALUE)
    Product getProductDetailsAsync(@PathVariable("productId") Long productId){
        Product productDetailsResponse = productInfoService.getProductAsync(productId)
        log.info("action=getProductDetails,${objectMapper.writeValueAsString(productDetailsResponse)}")
        return productDetailsResponse
    }

    @CacheEvict(value = "productInfo", allEntries=true)
    @ApiOperation(value = "Update product current price ", response = Product.class, tags = ["Update Product Current Price"])
    @PutMapping(value = ['{productId}'], consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Product updateProductPrice(
            @RequestBody @Valid Product updateRequest, BindingResult bindingResults,
            @PathVariable(value="productId") Long productId) {
        validateRequest(bindingResults, productId, updateRequest)
        Product updatedProductDetails = productPriceService.updateProductPrice(updateRequest)
        log.info("action=updateProductPriceResponse,${objectMapper.writeValueAsString(updatedProductDetails)}")
        return updatedProductDetails
    }

    static void validateRequest(BindingResult bindingResults, Long productId, Product priceUpdateRequest) {
        if (bindingResults.hasErrors()) {
            throw new InvalidPriceUpdateException(bindingResults)
        } else if (productId != priceUpdateRequest.id) {
            throw new PreconditionFailedException(priceUpdateRequest.id)
        }
    }
}
