package com.myRetail.app.service

import com.myRetail.app.domain.CurrentPrice
import com.myRetail.app.domain.Product
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

/**
 * This service is responsible for consolidating product details from RedSky(Produce Name) & Cassandra(Product Price)
 */
@Service
@Slf4j
class ProductInfoService {

    @Autowired
    ProductPriceService productPriceService

    @Autowired
    RedSkyService redSkyService


    Product getProductAsync( Long id) {
        CompletableFuture<String> productNameAsync = redSkyService.getProductNameAsync(id)
        CompletableFuture<CurrentPrice> priceAsync = productPriceService.getPriceAsync(id)
        CompletableFuture<Product> productCompletableFuture = priceAsync.thenCombine(productNameAsync) { CurrentPrice price, name ->
            new Product(id: id, name: name, current_price: new CurrentPrice(value: price.value, currency_code: price.currency_code))
        }
        try {
        productCompletableFuture.get()
        } catch (ExecutionException | InterruptedException exception) {
            log.error("Exception occured while accessing product details aysnc, exceptionClass=${exception?.cause?.class?.simpleName}"+
                    exception?.cause)
            throw exception?.cause
        }
    }


}
