package com.myRetail.app.exception

import groovy.transform.CompileStatic

@CompileStatic
class GenericException extends RuntimeException{

    String errorCode
    Object[] params

}
