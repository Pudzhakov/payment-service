package ru.service.test.payment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


@Configuration
@EnableAsync
class AsyncConfig {

    @Bean
    fun threadPoolTaskExecutor(): Executor {

        val executor = ThreadPoolTaskExecutor()
        executor.maxPoolSize = 2
        executor.corePoolSize = 2
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("Service-")
        executor.initialize()
        return executor

    }

}