/*
package com.aplinotech.cadastrocliente.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
//	private static final String USERNAME = "a";
//	private static final String PASSWORD = "a";
//	private static final String ROLE 	 = "ADMIN";
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.inMemoryAuthentication()
//			.withUser(USERNAME)
//			.password(PASSWORD)
//			.roles(ROLE);
//	}

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layout/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/instalar","/ativar").permitAll()
			.anyRequest()
			.authenticated()
			.and().formLogin().loginPage("/login").defaultSuccessUrl("/")
			.permitAll()
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
}
*/