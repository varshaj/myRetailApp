package com.myRetail.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.Executor

@Configuration
class ThreadpoolConfig {

    @Value('${threadpool.corepoolsize}')
    int corePoolSize

    @Value('${threadpool.maxpoolsize}')
    int maxPoolSize

    @Value('${threadpool.queueCapacity}')
    int queueCapacity

    @Bean
    public Executor taskExecutor () {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize)
        executor.setMaxPoolSize(maxPoolSize)
        executor.setQueueCapacity(queueCapacity)
        executor.setThreadNamePrefix("myRetail-")
        executor.initialize()
        return executor
    }
}
