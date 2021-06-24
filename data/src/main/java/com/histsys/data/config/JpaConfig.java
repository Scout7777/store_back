package com.histsys.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration("storeJpaConfig")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "storeEntityManagerFactory",
        transactionManagerRef = "storeTransactionManager",
        basePackages = {"com.histsys.data"})
public class JpaConfig {
    /**
     * EntityManagerFactory类似于Hibernate的SessionFactory,mybatis的SqlSessionFactory
     * 总之,在执行操作之前,我们总要获取一个EntityManager,这就类似于Hibernate的Session,
     * mybatis的sqlSession.
     *
     * @return
     */

    @Autowired
    @Qualifier("storeDataSource")
    private DataSource dataSource;

    @Bean(name = "storeEntityManagerFactory")
    public EntityManagerFactory storeEntityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.histsys.data");
        factory.setDataSource(dataSource);//数据源
        factory.setJpaPropertyMap(properties());
        factory.afterPropertiesSet();//在完成了其它所有相关的配置加载以及属性设置后,才初始化
        return factory.getObject();
    }

    private Map<String, Object> properties() {
        JpaProperties properties = new JpaProperties();
        properties.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        properties.setShowSql(true);
        properties.setDatabase(Database.POSTGRESQL);
        properties.setGenerateDdl(true);
        HibernateProperties hibernateProperties = new HibernateProperties();
        hibernateProperties.setDdlAuto("update");
        return hibernateProperties.determineHibernateProperties(properties.getProperties(), new HibernateSettings());
    }

    /**
     * 配置事物管理器
     *
     * @return
     */
    @Bean(name = "storeTransactionManager")
    public PlatformTransactionManager storeTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(this.storeEntityManagerFactory());
        return jpaTransactionManager;
    }
}
