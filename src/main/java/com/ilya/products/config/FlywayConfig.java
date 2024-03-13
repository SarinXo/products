package com.ilya.products.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Добавляет миграции FlyWay для базы данных. Информацию о базе данных берет из yaml файла
 */
@Configuration
@ConfigurationProperties(prefix = "datasource.app")
public class FlywayConfig extends HikariConfig {

    /**
     * Конфигурирование соединения с базой данных
     * @return HikariDataSource
     */
    @Bean
    @FlywayDataSource
    public DataSource appDataSource(){
        return new HikariDataSource(this);
    }

}
