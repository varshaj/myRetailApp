package com.myRetail.app.advice


import com.fasterxml.jackson.core.JsonParseException
import com.github.tomakehurst.wiremock.common.JsonException
import com.myRetail.app.exception.UnauthorizedException
import com.myRetail.app.exception.ErrorCodes
import com.myRetail.app.exception.ErrorResponse
import com.myRetail.app.exception.ApplicationException
import com.myRetail.app.exception.ValidationException
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
    List<ErrorResponse> handleGeneralException(Throwable t) {
        log.error("Unexpected Exception, exceptionClass=${t.class}, exception=${t.message}, cause=${t?.cause?.message}", t)
        [new ErrorResponse(errorCode: ErrorCodes.DEFAULT_ERROR_CODE, errorMessage: [ErrorCodes.DEFAULT_ERROR_DESCRIPTION])]
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleInvalidPriceUpdateException(ValidationException invalidRequestException) {
        return handleApplicationException(invalidRequestException)
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("action=handleHttpMessageNotReadableException", exception)
        return new ErrorResponse(
                errorCode: "INVALID_REQUEST",
                errorMessage: ["Message format is invalid"]
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [JsonParseException.class, JsonException.class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    List<ErrorResponse> handleBadJson(Exception e) {
        log.error("Invalid json in request, exception=${e.message}")
        invalidInputError
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ErrorResponse handleAuthenticationException(UnauthorizedException authenticationException) {
        [new ErrorResponse(errorCode: authenticationException.message, errorMessage: 'Invalid ClientId/Secret')]
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
                errorCode: ErrorCodes.INVALID_PRODUCT_ID,
                errorMessage: [ErrorCodes.INVALID_PRODUCT_ID_DESCRIPTION]
        )
    }

    private ErrorResponse handleApplicationException(ApplicationException exception) {
        log.error("action=handle${exception.class.simpleName}", exception)
        return new ErrorResponse(
                errorCode: exception.errorCode,
                errorMessage: exception.errorMessage
        )
    }

    private static List<ErrorResponse> getInvalidInputError() {
        [new ErrorResponse(errorCode: ErrorCodes.VALIDATION_FAILURE_ERROR_CODE, errorMessage: ['Invalid Input error'])]
    }

}
