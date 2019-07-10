package com.myRetail.app.config

import com.myRetail.app.interceptor.AuthenticateInterceptor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.validation.MessageCodesResolver
import org.springframework.validation.Validator
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@Slf4j
@EnableWebMvc
class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    AuthenticateInterceptor authenticationInterceptor

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns('/**')
                .excludePathPatterns('/info/**','/error','/swagger-resources/**','/v2/api-docs/**')
    }

    @Override
    void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {

    }

    @Override
    void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

    }

    @Override
    void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {

    }

    @Override
    void addFormatters(FormatterRegistry formatterRegistry) {

    }

    @Override
    void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    }

    @Override
    void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Override
    void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

    }

    @Override
    void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    void configureMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    Validator getValidator() {
        return null
    }

    @Override
    MessageCodesResolver getMessageCodesResolver() {
        return null
    }
}
