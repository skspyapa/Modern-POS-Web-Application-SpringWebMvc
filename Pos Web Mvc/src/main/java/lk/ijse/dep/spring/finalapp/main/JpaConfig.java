package lk.ijse.dep.spring.finalapp.main;


import lk.ijse.dep.spring.finalapp.repository.CustomerRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
public class JpaConfig {
    @Autowired
    private Environment env;
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds,JpaVendorAdapter adapter){
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(ds);
        lcemfb.setJpaVendorAdapter(adapter);
        lcemfb.setPackagesToScan("lk.ijse.dep.spring.finalapp");
        return lcemfb;
    }
    @Bean
    public DataSource dataSource(){
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(env.getRequiredProperty("javax.persistence.jdbc.driver"));
        bds.setUsername(env.getRequiredProperty("javax.persistence.jdbc.user"));
        bds.setPassword(env.getRequiredProperty("javax.persistence.jdbc.password"));
        bds.setUrl(env.getRequiredProperty("javax.persistence.jdbc.url"));
        bds.setMaxTotal(Integer.parseInt(env.getRequiredProperty("pool.max_connection")));
        bds.setMaxIdle(Integer.parseInt(env.getRequiredProperty("pool.max_idle")));
        bds.setInitialSize(Integer.parseInt(env.getRequiredProperty("pool.initial_size")));

        return bds;
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
        adapter.setShowSql(Boolean.getBoolean(env.getRequiredProperty("hibernate.show_sql")));
        adapter.setGenerateDdl(env.getRequiredProperty("hibernate.hbm2ddl.auto").equalsIgnoreCase("update")?true:false);
        return adapter;
    }
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
