package com.histsys.data.config;

import io.github.biezhi.anima.Anima;
import io.github.biezhi.anima.dialect.PostgreSQLDialect;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * 数据库 - anima 查询启动类
 */
@Slf4j
@Component
public class AnimaConfig {
    @Autowired
    @Qualifier("storeDataSource")
    private DataSource dataSource;

    @Bean
    private Anima initAnima() {
        log.info("Anima Database Start.");
        Anima anima = Anima.open(dataSource);
        anima.dialect(new PostgreSQLDialect()); // 分页代理
        return anima;
    }
}
