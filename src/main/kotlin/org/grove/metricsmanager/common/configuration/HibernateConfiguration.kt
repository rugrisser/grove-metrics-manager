package org.grove.metricsmanager.common.configuration

import org.hibernate.dialect.PostgreSQLDialect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Properties
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class HibernateConfiguration(
    private val dataSource: DataSource,
) {

    @Bean
    fun sessionFactory(): LocalSessionFactoryBean =
        LocalSessionFactoryBean().apply {
            setDataSource(dataSource)
            hibernateProperties = HIBERNATE_PROPERTIES
        }

    @Bean
    fun hibernateTransactionManager(): PlatformTransactionManager =
        HibernateTransactionManager().apply {
            sessionFactory = sessionFactory().`object`
        }

    private companion object {
        val HIBERNATE_PROPERTIES = Properties().apply {
            setProperty("hibernate.dialect", PostgreSQLDialect::class.qualifiedName)
            setProperty("hibernate.hbm2ddl.auto", "none")
        }
    }
}
