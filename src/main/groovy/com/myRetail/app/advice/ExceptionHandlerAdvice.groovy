package com.myRetail.app.advice


import com.fasterxml.jackson.core.JsonParseException
import com.github.tomakehurst.wiremock.common.JsonException
import com.myRetail.app.exception.AuthenticationException
import com.myRetail.app.exception.ErrorCodes
import com.myRetail.app.exception.ErrorResponse
import com.myRetail.app.exception.GenericException
import com.myRetail.app.exception.InvalidPriceUpdateException
import com.myRetail.app.exception.PreconditionFailedException
import com.myRetail.app.exception.ResourceNotFoundException
import groovy.util.logging.Slf4j
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.RestClientException


/**
 * Global error handling advice for all controllers.  Known exceptions can be added here to map specific response codes.
 */
@Slf4j
@ControllerAdvice
class ExceptionHandlerAdvice {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(Ordered.LOWEST_PRECEDENCE)
    List<Error> handleGeneralException(Throwable t) {
        log.error("Unexpected Exception, exceptionClass=${t.class}, exception=${t.message}, cause=${t?.cause?.message}", t)
        [new Error(errorCode: ErrorCodes.DEFAULT_ERROR_CODE, errorDescription: ErrorCodes.DEFAULT_ERROR_DESCRIPTION)]
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleInvalidPriceUpdateException(InvalidPriceUpdateException invalidPriceUpdateException) {
        return handleApplicationException(invalidPriceUpdateException)
    }

    @ResponseBody
    @ExceptionHandler(value = [JsonParseException.class, JsonException.class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<Error> handleBadJson(Exception e) {
        log.error("Invalid json in request, exception=${e.message}")
        invalidInputError
    }


    private static List<Error> getInvalidInputError() {
        [new Error(errorCode: ErrorCodes.VALIDATION_FAILURE_ERROR_CODE, errorDescription: 'Invalid Input error')]
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ErrorResponse handleAuthenticationException(AuthenticationException authenticationException) {
        [new Error(errorCode: authenticationException.message, errorDescription: 'Invalid ClientId/Secret')]
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    ErrorResponse handlePreconditionFailedException(PreconditionFailedException preconditionFailedException) {
        return handleApplicationException(preconditionFailedException)
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return handleApplicationException(resourceNotFoundException)
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleRestClientException(RestClientException restClientException) {
        log.error("action=handleRestClientException", restClientException)
        return new ErrorResponse(
                errorCode: "INVALID_PRODUCT_ID",
                errorMessage: "Invalid productId"
        )
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleRuntimeException(RuntimeException exception) {
        log.error("action=handleRuntimeException", exception)
        return new ErrorResponse(
                errorCode: "INTERNAL_EXCEPTION",
                errorMessage: "Exception occured at runtime"
        )
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("action=handleHttpMessageNotReadableException", exception)
        return new ErrorResponse(
                errorCode: "INVALID_REQUEST",
                errorMessage: "Message format is invalid"
        )
    }

    ErrorResponse handleApplicationException(GenericException exception) {
        log.error("action=handle${exception.class.simpleName}", exception)
        return new ErrorResponse(
                errorCode: exception.errorCode,
                errorMessage: "Exception occured while processing request with params = ${exception.params.toString()}"
        )
    }

}
