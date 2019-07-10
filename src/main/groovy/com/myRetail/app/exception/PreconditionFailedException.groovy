package com.myRetail.app.exception

import groovy.transform.CompileStatic

@CompileStatic
class PreconditionFailedException extends GenericException {

    PreconditionFailedException(Long productId) {
        errorCode = "PRODUCT_ID_MISMATCH"
        params = [productId] as String[]
    }
}
