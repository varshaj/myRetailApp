package com.myRetail.app.exception

import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

@CompileStatic
class ValidationException extends ApplicationException {

    ValidationException(String message, BindingResult bindingResults) {
        super(message,  ErrorCodes.VALIDATION_FAILURE_ERROR_CODE,
                [bindingResults?.fieldErrors?.first()?.field, bindingResults?.fieldErrors?.first()?.defaultMessage] as String[])
    }
}
