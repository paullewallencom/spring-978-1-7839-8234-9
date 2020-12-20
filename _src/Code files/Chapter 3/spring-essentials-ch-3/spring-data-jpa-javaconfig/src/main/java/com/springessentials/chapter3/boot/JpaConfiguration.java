package com.springessentials.chapter3.boot;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = { "com.springessentials.chapter3" })
@ImportResource("classpath:/META-INF/spring/app-context.xml")
// @PropertySource(name = "postgresql-ds", value =
// "classpath:/META-INF/spring/postgresql-ds.properties")
@EnableJpaRepositories(basePackages = "com.springessentials.chapter3.dao")
@EnableJpaAuditing
public class JpaConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class);

	@Resource(name = "postgresql-ds")
	private Properties pgDsProps;

	@Autowired
	private DataSource dataSource;

	@Bean
	DataSource getDatasource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource(pgDsProps.getProperty("url"));
		dataSource.setDriverClassName(pgDsProps.getProperty("driverClassName"));
		dataSource.setUsername(pgDsProps.getProperty("username"));
		dataSource.setPassword(pgDsProps.getProperty("password"));
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
		lef.setPackagesToScan("com.springessentials.chapter3.domain");
		lef.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		lef.setValidationMode(ValidationMode.NONE);

		return lef;
	}

}
