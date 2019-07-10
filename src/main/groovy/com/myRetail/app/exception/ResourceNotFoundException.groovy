package com.myRetail.app.exception

import groovy.transform.CompileStatic

@CompileStatic
class ResourceNotFoundException extends GenericException {

    ResourceNotFoundException(Long productId) {
        errorCode = "PRODUCT_NOT_FOUND"
        params = [productId] as String[]
    }

    ResourceNotFoundException() {
        errorCode = "PRODUCT_NOT_FOUND"
        params = null
    }
}
