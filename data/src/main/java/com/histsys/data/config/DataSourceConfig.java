package com.histsys.data.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application-store[${spring.profiles.active}].properties")
public class DataSourceConfig {

    @Bean(name = "storeDataSource")
    @Qualifier("storeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.store")
    public javax.sql.DataSource dataSource(@Value("${spring.datasource.store.url}") String url,
                                           @Value("${spring.datasource.store.username}") String username,
                                           @Value("${spring.datasource.store.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

//    public javax.sql.DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("");
//        dataSource.setUrl(Env.get("HISTSYS_DB_URL", "jdbc:postgresql://localhost:5433/histsys_dev"));
//        dataSource.setUsername(Env.get("HISTSYS_DB_USERNAME", "neo"));
//        dataSource.setPassword(Env.get("HISTSYS_DB_PASSWORD", ""));
//        return dataSource;
//    }
}
