package com.myRetail.app.exception

import groovy.transform.CompileStatic

@CompileStatic
class ErrorResponse {

    String errorCode
    Object[] errorMessage
}
