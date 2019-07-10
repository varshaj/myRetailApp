package com.myRetail.app.exception

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic

@CompileStatic
class ApplicationException extends RuntimeException{

    List<ErrorResponse> errors
    String errorCode
    Object[] errorMessage

    ApplicationException(String message, String errorCode, Object[] errorDescription) {
        super(message)
        this.errorCode = errorCode
        this.errorMessage = errorDescription
    }

    ApplicationException(String message, List<ErrorResponse> errors) {
        super(message)
        this.errors = errors
    }

    public String getFormattedString() {
        "${message}; errors=${new ObjectMapper().writeValueAsString([errors])}"
    }

}
