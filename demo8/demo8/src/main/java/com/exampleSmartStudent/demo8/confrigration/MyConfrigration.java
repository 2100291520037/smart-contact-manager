package com.exampleSmartStudent.demo8.confrigration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfrigration {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl()
				;
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

@Bean
public AuthenticationProvider authenticationProvider()
{
	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	provider.setUserDetailsService(userDetailsService);
	provider.setPasswordEncoder(passwordEncoder());
	return  provider;
}

		@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	return httpSecurity
			.csrf(AbstractHttpConfigurer:: disable)
			.authorizeHttpRequests(request-> {
				//request.requestMatchers("/home").permitAll();
				request.requestMatchers("/admin/**").hasRole("ADMIN");
				request.requestMatchers("/user/**").hasRole("USER");
				request.anyRequest().permitAll();
			})
			 // .formLogin(AbstractAuthenticationFilterConfigurer:: permitAll)
			.formLogin(httpSecurityFormLoginConfigurer->
					{
						httpSecurityFormLoginConfigurer.loginPage("/signin")
								.successHandler(new AuthenticationSuccessHandler())
								.permitAll();
					}
			)
			.build();
}





}