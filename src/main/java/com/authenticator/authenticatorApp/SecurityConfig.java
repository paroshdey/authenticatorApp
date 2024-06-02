package com.authenticator.authenticatorApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.authenticator.authenticatorApp.service.CustomOAuth2UserService;
import com.authenticator.authenticatorApp.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
	@Autowired
    private CustomOAuth2UserService oauth2UserService;
     
    @Autowired
    private OauthSuccessHandler oauthLoginSuccessHandler;
     
    @Autowired
    private LocalSuccessHandler databaseLoginSuccessHandler;
    
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http
		 .exceptionHandling(e -> e
				 .accessDeniedPage("/403"))
		 .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
		 .csrf(csrf -> csrf
				 .disable())
         .authorizeHttpRequests(authz -> authz
        		 .requestMatchers("/","/**", "/login", "/oauth/**" ,"/console/**").permitAll()
        		 .anyRequest().authenticated())
		 .formLogin(formLogin -> formLogin
	                .loginPage("/login")
	                .permitAll()
	                .usernameParameter("email")
	                .passwordParameter("password")
	                .successHandler(databaseLoginSuccessHandler))
		 .logout(logout -> logout
				 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				 .permitAll())
		 .oauth2Login(oauth2Login -> oauth2Login
				 .loginPage("/login")
				 .successHandler(oauthLoginSuccessHandler)
				 .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
						 .userService(oauth2UserService)));
				 
 return http.build();
	
	
	}

}
