package configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    @Bean
    @DependsOn("springLiquibase")
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[]{"*"});

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
    public SpringLiquibase springLiquibase() throws NamingException {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:/liquibase/rootChangelog.xml");
        springLiquibase.setDataSource(dataSource());

        return springLiquibase;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/lets_talk?characterEncoding=UTF-8&createDatabaseIfNotExist=true");
        dataSource.setUsername("museum");
        dataSource.setPassword("museum");

        return dataSource;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() throws NamingException {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        jpaTransactionManager.setDataSource(dataSource());
        return jpaTransactionManager;
    }
}
