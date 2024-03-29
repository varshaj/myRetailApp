package com.myRetail.app

import com.myRetail.app.config.RootConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@Import([RootConfig])
@EnableCaching
@EnableAsync
class MyRetailApplication extends SpringBootServletInitializer {

	static void main(String[] args) {
		SpringApplication.run(MyRetailApplication, args)
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MyRetailApplication.class)
	}


}
