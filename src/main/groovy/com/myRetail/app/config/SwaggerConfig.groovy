package com.myRetail.app.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import static springfox.documentation.builders.PathSelectors.regex

@Configuration
@Profile('swagger')
@EnableSwagger2
class SwaggerConfig {
    static final String detailDescription = "The Retail App Microservice` is a RESTful API that provides detils/updates price" +
            "of a specific product by id. \n \n"+
            "Below is a list of available REST API calls for accessing the product "
    @Bean
    public Docket myRetailApi() {
    return new Docket(DocumentationType.SWAGGER_2)
    .select()
    .apis(RequestHandlerSelectors.basePackage("com.myRetail.app"))
    .paths(PathSelectors.any())
    .build()
    .apiInfo(apiInfo())


}

private ApiInfo apiInfo() {
    new ApiInfo("MyRetailApp Product Information API",
            detailDescription +
                    "[Source Code](https://github.com/varshaj/myRetailApp)", "",
            "", new Contact("", "", "varsha.jaiswal@gmail.com"),
            "", "")
}



}
