package com.taskify.boot;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("com.taskify")
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.taskify.dao")
@PropertySource(name = "postgresql-ds", value = "classpath:/postgresql-ds.properties")
public class WebAppConfig extends WebMvcConfigurerAdapter {

	// @Resource(name = "postgresql-ds")
	// private Properties pgDsProps;

	@Autowired
	private Environment env;

	@Autowired
	private DataSource dataSource;

	@Bean
	DataSource getDatasource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUsername(env.getProperty("username"));
		dataSource.setPassword(env.getProperty("password"));
		return dataSource;
	}

	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.dialect", PostgreSQL94Dialect.class.getName());
		// props.put("hibernate.cache.provider_class",
		// HashtableCacheProvider.class.getName());
		props.put("hibernate.hbm2ddl.auto", "create");
		return props;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		return hibernateJpaVendorAdapter;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(localContainerEntityManagerFactoryBean().getObject());
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setDataSource(this.dataSource);
		lef.setJpaPropertyMap(this.jpaProperties());
		lef.setJpaVendorAdapter(this.jpaVendorAdapter());
		lef.setPackagesToScan("com.taskify.domain");
		lef.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		lef.setValidationMode(ValidationMode.NONE);

		return lef;
	}

}
