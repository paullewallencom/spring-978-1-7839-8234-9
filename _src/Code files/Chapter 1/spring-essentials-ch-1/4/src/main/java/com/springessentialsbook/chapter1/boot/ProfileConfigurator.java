package com.springessentialsbook.chapter1.boot;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ComponentScan(basePackages = "com.springessentialsbook")
public class ProfileConfigurator {
	
	@Value("http://country.io/names.json")
	private Resource countriesResource;
	
	@Bean
	@Profile("dev")
	public DataSource devDataSource() {
	    return new EmbeddedDatabaseBuilder()
	        .setType(EmbeddedDatabaseType.HSQL)
	        .addScript("tasks-system-schema.sql")
	        .addScript("tasks-master-data.sql")
	        .build();
	}
	
    @Bean
    @Profile("prod")
    public DataSource productionDataSource() throws Exception {
        Context ctx = new InitialContext();
        return (DataSource) ctx.lookup("java:comp/env/jdbc/datasource/tasks");
    }

}
