package com.poissontata.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("jawad").password("1234").roles("ADMIN","USER");
		//auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
		.authoritiesByUsernameQuery("select user as principal, role from users_roles where user=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(new Md5PasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers(
				"/clients",
				"/searchFournisseur",
				"/searchClient",
				"/clientsManagment",
				"/fournisseurs",
				"/addFournisseur",
				"/saveClient",
				"/editClient",
				"/editFournisseur",
				"/saveEditFournisseur",
				"/addFacture",
				"/saveFacture",
				"/addBon",
				"/saveBon",
				"/addPaymentFournisseur",
				"/savePayemntFournisseur",
				"/addVirementClient",
				"/saveVirmentClient",
				"/deleteOperation",
				"/editFacture",
				"/saveEditFacture",
				"/editBon",
				"/saveEditBon",
				"/editPaymentFournisseur",
				"/saveEditPaymentFournisseur",
				"/editVirmentClient",
				"/saveEditVirmentClient",
				"/addReglement").hasRole("AGENT");
		http.authorizeRequests().antMatchers(
				"/users"
				).hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/","/home").hasRole("USER");
		http.exceptionHandling().accessDeniedPage("/accessDenied");
	}
}

