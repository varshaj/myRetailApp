package com.myRetail.app.config

import com.myRetail.app.exception.RestTemplateResponseErrorHandler
import com.myRetail.app.interceptor.AuthenticateInterceptor
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@CompileStatic
@Configuration
@Import(value = [SwaggerConfig, InterceptorConfig, ThreadpoolConfig])
class RootConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler errorHandler) {
        builder.setConnectTimeout(1000)
        builder.setReadTimeout(5000)
        builder.errorHandler(errorHandler)
        return builder.build()
    }

}
