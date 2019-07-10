package com.myRetail.app.service

import com.myRetail.app.client.RedSkyClient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture


@Service
class RedSkyService {

    @Autowired
    RedSkyClient client

    String getProductName(long id) {
        client.getProductDescription(id)
    }

    @Async
    CompletableFuture<String> getProductNameAsync(long id) {
        CompletableFuture.completedFuture(getProductName(id))
    }

}
