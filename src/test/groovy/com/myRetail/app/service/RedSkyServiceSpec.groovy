package com.myRetail.app.repository

import com.myRetail.app.client.RedSkyClient

import com.myRetail.app.service.RedSkyService
import spock.lang.Specification

class RedSkyServiceSpec extends Specification {
    RedSkyClient redSkyClient = Mock()

    RedSkyService service = new RedSkyService(
            client: redSkyClient
    )
    Long productId = 100L
    String productName = 'Big Screen TV'

    def 'getProductName - Happy Path' () {

        when:
        String productName = service.getProductName(productId)

        then:
        1 * redSkyClient.getProductDescription(productId) >> 'test description'
        0 * _

        then:
        productName == productName
    }

    def 'getProductName - Invalid product id' () {
        given:

        when:
        String productName = service.getProductName(productId)

        then:
        1 * redSkyClient.getProductDescription(productId)>>null
        0 * _

        then:
        !productName
    }
}
