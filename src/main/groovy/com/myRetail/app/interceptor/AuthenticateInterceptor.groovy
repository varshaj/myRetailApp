package com.myRetail.app.interceptor

import com.myRetail.app.exception.AuthenticationException
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static com.myRetail.app.common.Constants.Headers

@Slf4j
@Component
class AuthenticateInterceptor implements HandlerInterceptor {

    @Value('${product.api.clientId}')
    String clientId

    @Value('${product.api.clientSecret}')
    String clientSecret

    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.method == HttpMethod.GET.name()) {
            return true
        }
        String authKey = request.getHeader(Headers.HTTP_HEADER_CLIENT_ID.toString())
        String authSecret = request.getHeader(Headers.HTTP_HEADER_CLIENT_SECRET.toString())

        if (!(clientId.equalsIgnoreCase(authKey) && clientSecret.equalsIgnoreCase(authSecret))) {
            log.warn('clientId/clientSecret did not match')
            throw new AuthenticationException()
        }
        true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
