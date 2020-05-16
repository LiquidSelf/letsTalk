package configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.*;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    @DependsOn("springLiquibase")
    public LocalContainerEntityManagerFactoryBean factoryBean(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("*");

//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(dataSource());
//        factoryBean.setPackagesToScan("kz.jazzsoft.bnd.eas");
//        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
//        Map<String, String> map = new HashMap<>();
//        map.put("hibernate.dialect", hibernateDialect);
//        map.put("hibernate.generate_statistics", hibernateSqlDebug);
//        map.put("hibernate.show_sql", hibernateSqlDebug);
//        map.put("hibernate.format_sql", hibernateSqlDebug);
//        map.put("hibernate.cache.provider_class", hibernateCacheSecondProvider);
//        map.put("hibernate.cache.region.factory_class", hibernateCacheSecondRegion);
//        map.put("hibernate.cache.use_second_level_cache", hibernateCacheSecond);
//        map.put("hibernate.cache.use_query_cache", hibernateCacheSecond);
//        factoryBean.setJpaPropertyMap(map);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(HBM2DDL_AUTO, "validate");
        hibernateProperties.setProperty(DIALECT, "org.hibernate.dialect.MySQLDialect");
        factoryBean.setJpaProperties(hibernateProperties);

        return factoryBean;
    }

    @Bean
    @DependsOn("dataSource")
    public SpringLiquibase springLiquibase(DataSource dataSource) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:/liquibase/rootChangelog.xml");
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/ltDb");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
        jndiObjectFactoryBean.afterPropertiesSet();
        return (DataSource) jndiObjectFactoryBean.getObject();
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager(DataSource dataSource, LocalContainerEntityManagerFactoryBean factoryBean){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(factoryBean.getObject());
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }
}
