package com.myRetail.app.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.myRetail.app.domain.CurrentPrice
import com.myRetail.app.domain.Product
import com.myRetail.app.exception.PreconditionFailedException
import com.myRetail.app.exception.ResourceNotFoundException
import com.myRetail.app.exception.ValidationException
import com.myRetail.app.service.ProductInfoService
import com.myRetail.app.service.ProductPriceService
import com.myRetail.app.service.RedSkyService
import org.springframework.validation.BindingResult
import spock.lang.Specification
import spock.lang.Unroll

class ProductInfoControllerSpec extends Specification {

    RedSkyService redSkyService = Mock(RedSkyService)
    ProductInfoService productInfoService = Mock(ProductInfoService)
    ProductPriceService productPriceService = Mock(ProductPriceService)

    ProductInfoController controller =  new ProductInfoController(redSkyService: redSkyService,
            productPriceService: productPriceService,
            productInfoService: productInfoService,
            objectMapper: new ObjectMapper()
    )

    Long productId = 6989L
    Long price = 11.00
    String productName = 'test product'

    def 'getProductDetails -Happy path'() {
        when:
        Product productDetails = controller.getProductDetails(productId)

        then:
        1 * redSkyService.getProductName(productId) >> productName
        1 * productPriceService.getProductPrice(productId) >> new CurrentPrice(value: price, currency_code: 'USD')
        0 * _
        productDetails.name == productName
        productDetails.current_price.value == price
    }

    def 'getProductDetails - throw an error if the product Id is not found'() {
        when:
        Product productDetails = controller.getProductDetails(productId)

        then:
        1 * redSkyService.getProductName(productId) >> { throw new ResourceNotFoundException("No product found for ${productId}") }
        thrown(ResourceNotFoundException)
        0 * _
        !productDetails
    }

    def 'updateProductPrice-Happy Path'() {

        given:
        BindingResult bindingResults = Mock(BindingResult)
        bindingResults.hasErrors() >> false

        Product productDetails = new Product(
                id: 124L,
                name: "The Big Lebowski (Blu-ray)",
                current_price: new CurrentPrice(
                        value: 12.99,
                        currency_code: "USD"
                )
        )

        when:
        Product response = controller.updateProductPrice(productDetails, bindingResults, 124L)

        then:
        1 * productPriceService.updateProductPrice(productDetails) >> new Product(
                id: 124L,
                name: "The Big Lebowski (Blu-ray)",
                current_price: new CurrentPrice(
                        value: 12.99,
                        currency_code: "USD"
                )

        )
        response.id == 124L
        response.name == "The Big Lebowski (Blu-ray)"
        response.current_price.value == 12.99
        response.current_price.currency_code == "USD"
    }

    def 'updateProductPrice-Bad request'() {
        given:
        BindingResult bindingResults = Mock(BindingResult)
        bindingResults.hasErrors() >> true

        Product productDetails = new Product(
                id: id,
                name: name,
                current_price: new CurrentPrice(
                        value: value,
                        currency_code: "USD"
                )
        )

        when:
        Product response = controller.updateProductPrice(productDetails, bindingResults, id)

        then:
        ValidationException validationException = thrown()
        validationException.errorCode == 'E400'
        0 * productPriceService.updateProductPrice(productDetails)

        where:
        id       | name    | value
        null     | 'name'  | 12.10
        1L       | 'name'  | null
    }

    def 'updateProductPrice-ProductId mismatch'() {
        given:
        BindingResult bindingResults = Mock(BindingResult)
        bindingResults.hasErrors() >> false

        Product productDetails = new Product(
                id: 123L,
                name: 'Test product',
                current_price: new CurrentPrice(
                        value: 11.01,
                        currency_code: "USD"
                )
        )

        when:
        controller.updateProductPrice(productDetails, bindingResults, 567L)

        then:
        PreconditionFailedException preconditionFailedException = thrown()
        preconditionFailedException.errorCode == 'E400'
        0 * productPriceService.updateProductPrice(productDetails)
    }
}
