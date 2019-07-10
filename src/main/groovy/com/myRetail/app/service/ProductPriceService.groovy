package com.myRetail.app.service

import com.myRetail.app.domain.CurrentPrice
import com.myRetail.app.domain.Product
import com.myRetail.app.exception.ResourceNotFoundException
import com.myRetail.app.repository.ProductInfoRepository
import com.myRetail.app.repository.ProductInfo
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture

@Service
@Slf4j
/**
 * This service facilitates getting/updating price of a product in database.
 */
class ProductPriceService {

    @Autowired
    ProductInfoRepository productInfoRepository

    /**
     * Method for getting product detail with current price.
     * @param id
     */
    CurrentPrice getProductPrice(Long productId) {
        ProductInfo productInfo = productInfoRepository.findByProductId(productId).getAt(0)
        if (!productInfo) {
            throw new ResourceNotFoundException(productId.toString())
        }

        log.info("action=getProductPrice, productId=${productId}, currentPrice=${productInfo?.currentPrice}")
        return new CurrentPrice(
                value: productInfo.currentPrice,
                currency_code: productInfo.currencyCode
        )
    }

    /**
     * Method for getting product detail with current price async.
     * @param id
     */
    @Async
    CompletableFuture<CurrentPrice> getPriceAsync(Long id) {
        CompletableFuture.completedFuture(getProductPrice(id))
    }

    /**
     * Method for price update. First check if record exists, retrieve the currentPrice from the db and update the price.
     * @param id
     * @param price
     * @param currencyCode
     */
    Product updateProductPrice (Product priceUpDateRequest) {
        ProductInfo productInfo = productInfoRepository.findByProductId(priceUpDateRequest.id)?.getAt(0)
        if (productInfo) {
            productInfo.currentPrice = priceUpDateRequest.current_price.value
            if (priceUpDateRequest?.current_price?.currency_code) {
                productInfo.currencyCode = priceUpDateRequest?.current_price?.currency_code
            }
            ProductInfo updatedPrice = productInfoRepository.save(productInfo)

            Product updatedProductDetails = new Product(
                    id: updatedPrice?.productId?: id,
                    name: updatedPrice.productName,
                    current_price: new CurrentPrice(
                            value: updatedPrice.currentPrice,
                            currency_code: updatedPrice?.currencyCode?:'USD'
                    )
            )

            log.info("action=updateProductPrice, productId=${updatedProductDetails?.id}, currentPrice=${updatedProductDetails?.current_price}")
            return updatedProductDetails

        } else {
            throw new ResourceNotFoundException("No Product found with productId ${priceUpDateRequest.id} ")
        }
    }

}
