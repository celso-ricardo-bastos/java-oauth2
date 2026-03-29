package com.github.AuthanticationServer.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfigurations {

    @Value("${spring.datasource.driver-class-name}")
    String driver;
    @Value("${spring.datasource.username}")
    String  username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.url}")
    String url;

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1);
        config.setMaxLifetime(6000000);
        config.setIdleTimeout(100000);
        config.setPoolName("library-db-pool");
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }
}
