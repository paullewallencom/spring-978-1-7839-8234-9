package com.taskify.boot;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
//@ImportResource("/spring/root-context.xml")
@EnableWebMvc
@ComponentScan(basePackages = {"com.taskify"})
@EnableJpaRepositories(basePackages = "com.taskify.dao")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

	@Autowired
	private DataSource dataSource;
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private TaskService taskService;

	@Bean
	DataSource getHsqlDatasource() {

		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
//		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
//				.addScript("db-scripts/hsql/db-schema.sql").addScript("db-scripts/hsql/data.sql")
//				.addScript("db-scripts/hsql/storedprocs.sql").addScript("db-scripts/hsql/functions.sql")
//				.setSeparator("/").build();
		//
		// DriverManagerDataSource dataSource = new
		// DriverManagerDataSource().setDriverClassName("org.hsqldb.jdbcDriver");
		// dataSource.setUrl("jdbc:hsqldb:hsql://localhost:");
		// dataSource.setUsername("sa");
		// dataSource.setPassword("");
		// return dataSource;
	}
	
	/**
	@Bean
	public PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	**/

	@Bean
	public JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
	
	

	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.dialect", HSQLDialect.class.getName());
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
