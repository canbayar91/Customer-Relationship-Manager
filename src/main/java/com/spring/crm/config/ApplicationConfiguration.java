package com.spring.crm.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.spring.crm")
@PropertySource("classpath:postgresql.properties")
public class ApplicationConfiguration implements WebMvcConfigurer {
	
	@Autowired
	private Environment environment;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean
	public DataSource dataSource() {
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		try {
			dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			logger.severe("JDBC Driver could not be loaded");
			throw new RuntimeException(e);
		}
		
		logger.info("Connection on URL: " + environment.getProperty("jdbc.url"));
		logger.info("Using user: " + environment.getProperty("jdbc.user"));
		
		dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
		dataSource.setUser(environment.getProperty("jdbc.user"));
		dataSource.setPassword(environment.getProperty("jdbc.password"));
		
		dataSource.setInitialPoolSize(getIntegerProperty("connection.pool.initialPoolSize"));
		dataSource.setMinPoolSize(getIntegerProperty("connection.pool.minPoolSize"));
		dataSource.setMaxPoolSize(getIntegerProperty("connection.pool.maxPoolSize"));
		dataSource.setMaxIdleTime(getIntegerProperty("connection.pool.maxIdleTime"));
		
		return dataSource;
	}
	
	private int getIntegerProperty(String propertyName) {
		String propertyValue = environment.getProperty(propertyName);
		return Integer.parseInt(propertyValue);
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		DataSource dataSource = dataSource();
		Properties hibernateProperties = getHibernateProperties();
		
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(environment.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;
	}
	
	private Properties getHibernateProperties() {

		Properties properties = new Properties();

		properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		
		return properties;				
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);

		return transactionManager;
	}	
}
