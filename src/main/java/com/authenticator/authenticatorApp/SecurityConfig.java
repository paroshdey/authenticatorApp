package com.authenticator.authenticatorApp;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.authenticator.authenticatorApp.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	 @Bean
	    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
	        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
	    }
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.exceptionHandling(e -> e.accessDeniedPage("/403"))
				.headers(headers -> headers
						.frameOptions(FrameOptionsConfig::disable))
				        .csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/", "/**", "/login**", "/oauth/**", "/console/**")
						.permitAll()
						.anyRequest()
						.authenticated())
				.formLogin(formLogin -> formLogin
				        .loginPage("/login")
				        .permitAll()
				        .usernameParameter("email")
						.passwordParameter("password")
						.successForwardUrl("/index"))
				.oauth2Login(login -> login
						.loginPage("/login")
						.defaultSuccessUrl("/oauth"))
				        
				.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
						.permitAll()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login"));
		
		return http.build();

	}

}
