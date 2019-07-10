package com.myRetail.app.exception

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus

@CompileStatic
class PreconditionFailedException extends ApplicationException {

    PreconditionFailedException(String message) {
        super(message,  ErrorCodes.PRODUCT_ID_MISMATCH, [ErrorCodes.PRODUCT_ID_MISMATCH_DESCRIPTION])
    }
}
