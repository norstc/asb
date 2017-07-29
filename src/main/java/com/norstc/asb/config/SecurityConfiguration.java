package com.norstc.asb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	

	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests()
			.antMatchers("/","/welcome","/stock/*","/owner/regist").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/owner/login").permitAll()
			.and()
			.logout().logoutUrl("/owner/logout").permitAll()
			.and()
			.authorizeRequests().antMatchers("/console/**").permitAll();
		
		//h2console
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN")
			.and()
			.withUser("user").password("user").roles("USER")
			.and()
			.withUser("tom").password("tom").roles("USER");
		
	}

}
