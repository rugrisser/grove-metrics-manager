package org.grove.metricsmanager.configuration

import com.zaxxer.hikari.HikariDataSource
import org.grove.metricsmanager.configuration.properties.HikariProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(HikariProperties::class)
class HikariConfiguration {

    @Bean
    fun hikariDataSource(properties: HikariProperties): DataSource =
         HikariDataSource(properties)
}
