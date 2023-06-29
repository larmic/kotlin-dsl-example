package de.larmic.kotlindsl.example

import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer

// https://github.com/testcontainers/testcontainers-java-spring-boot-quickstart
// debugging class to start application in development mode
// required postgres db will be automatically started
fun main(args: Array<String>) {
    fromApplication<Application>()
        .with(ContainersConfig::class.java)
        .run(*args)
}

@TestConfiguration(proxyBeanMethods = false)
class ContainersConfig {
    @Bean
    @ServiceConnection
    @RestartScope
    fun postgreSQLContainer(): PostgreSQLContainer<*>  = PostgreSQLContainer("postgres:15.3-alpine")
}