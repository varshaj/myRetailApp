package com.myRetail.app.client


import com.myRetail.app.exception.ResourceNotFoundException
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

import java.util.concurrent.CompletableFuture

@Slf4j
@Component
class RedSkyClient {
    @Autowired
    RestTemplate restTemplate

    @Value('${redskyProductEndpoint}')
    String redSkyUrl


    String getProductDescription(Long productId) throws ResourceNotFoundException {
        try {
            Map response = restTemplate.getForObject(redSkyUrl, Map.class, [id: productId])
            return response?.product?.item?.product_description?.title
        } catch (RestClientException e) {
            log.info("Exception occurred while accessing the redSkyService : " + redSkyUrl + " for productId : " + productId)
            throw new ResourceNotFoundException(productId)
        }
    }

    @Async
    CompletableFuture<String> getProductNameAsync(long productId) {
        CompletableFuture.completedFuture(getProductDescription(productId))
    }

}
