package org.grove.metricsmanager.common.configuration.properties

import com.zaxxer.hikari.HikariConfig
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("hikari")
class HikariProperties : HikariConfig()
