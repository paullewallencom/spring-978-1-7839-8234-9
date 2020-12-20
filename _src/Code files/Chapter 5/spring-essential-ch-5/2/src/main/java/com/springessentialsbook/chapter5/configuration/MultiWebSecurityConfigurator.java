package com.springessentialsbook.chapter5.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@ComponentScan(basePackages = "com.springessentialsbook.chapter5")
public class MultiWebSecurityConfigurator   {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}


	@Configuration
	protected static class LoginFormBasedWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private AuthenticationSuccessHandler authenticationSuccessHandler;

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("*.jsp").denyAll()
					.antMatchers("/", "/login").permitAll()
					.antMatchers("/user*//**").access("hasRole('USER') or hasRole('ADMIN')")
					.antMatchers("/admin*//**").access("hasRole('ADMIN')")
					.antMatchers("/accountant*//**").access("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
					.and().formLogin()
					.loginPage("/login").successHandler(authenticationSuccessHandler)
					.failureUrl("/nonAuthorized")
					.usernameParameter("username").passwordParameter("password").loginProcessingUrl("/login")
					.permitAll()
					.and()
					.logout()
					.logoutSuccessUrl("/")
					.permitAll();
		}
	}

	@Configuration
	@Order(1)
	public 	static 	class HttpBasicWebSecurityConfigurationAdapter 	extends 	WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) 	throws Exception {
            http.antMatcher("/adResources*/**").authorizeRequests().anyRequest().hasRole("ADMIN")
					.and()
					.httpBasic();
		}
	}



}
