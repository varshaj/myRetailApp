package com.myRetail.app.exception

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus

@CompileStatic
class ResourceNotFoundException extends ApplicationException {

    ResourceNotFoundException(String message) {
        super(message, ErrorCodes.PRODUCT_NOT_FOUND_CODE, [ErrorCodes.PRODUCT_NOT_FOUND_DESCRIPTION])
    }

    ResourceNotFoundException() {
        super('No resource found', ErrorCodes.PRODUCT_NOT_FOUND_CODE, [ErrorCodes.PRODUCT_NOT_FOUND_DESCRIPTION])
    }
}
