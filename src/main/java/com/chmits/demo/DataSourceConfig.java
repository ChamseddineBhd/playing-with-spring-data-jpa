package com.chmits.demo;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author Chamseddine Benhamed <chamseddine.bhd at gmail.com>
 */

@Configuration
@PropertySource(value = {"classpath:database.properties"})
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(Environment env) {
        String url = env.getRequiredProperty("scheme") + "://" + env.getRequiredProperty("hostPort")
                + "/" + env.getRequiredProperty("spring.jpa.database-name") + env.getProperty("query");

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getRequiredProperty("driverClassName"));
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(env.getRequiredProperty("login"));
        dataSourceBuilder.password(env.getRequiredProperty("password"));
        return dataSourceBuilder.build();
    }
}
