package com.myRetail.app.exception

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus

@CompileStatic
class UnauthorizedException extends ApplicationException {

    UnauthorizedException(String message) {
        super(message,  ErrorCodes.INVALID_CREDENTIALS, [ErrorCodes.INVALID_CREDENTIALS_DESCRIPTION])
    }
}
