package com.oauth2.google.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oauth2.google.security.jwt.TokenAuthenticationFilter;
import com.oauth2.google.security.oauth2.CustomOidcUserService;
import com.oauth2.google.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.oauth2.google.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomOidcUserService customOidcUserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().csrf().disable().formLogin().disable().httpBasic().disable()
				//.exceptionHandling()
				//.authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .authorizeRequests()
        .antMatchers("/", "/h2-console/**", "/error", "/api/all", "/api/auth/**", "/oauth2/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .logout()
        .logoutSuccessHandler(logoutSuccessHandler())
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .and()
        .oauth2Login()
        .authorizationEndpoint()
		.authorizationRequestRepository(cookieAuthorizationRequestRepository())
		//.and().redirectionEndpoint()
		.and().userInfoEndpoint()
		.oidcUserService(customOidcUserService)
		//.userService(customOAuth2UserService)
		.and()
		.successHandler(oAuth2AuthenticationSuccessHandler)
		.failureHandler(oAuth2AuthenticationFailureHandler);;*/
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().csrf().disable().formLogin().disable().httpBasic().disable()
				.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint())
				.and().authorizeRequests()
				.antMatchers("/", "/h2-console/**", "/error", "/api/all", "/api/auth/**", "/oauth2/**")
				.permitAll().anyRequest()
				.authenticated()
				.and().oauth2Login()
				.authorizationEndpoint()
				//.authorizationRequestRepository(cookieAuthorizationRequestRepository())
				//.and().redirectionEndpoint()
				.and().userInfoEndpoint()
				.oidcUserService(customOidcUserService)
				/* .logout(logout -> logout
			                .logoutSuccessHandler(oidcLogoutSuccessHandler())
			                .invalidateHttpSession(true)
			                .clearAuthentication(true)
			                .deleteCookies("JSESSIONID","CSRF-TOKEN","XSRF-TOKEN")
			                .permitAll())*/
				//.userService(customOAuth2UserService)
				.and()
				.successHandler(oAuth2AuthenticationSuccessHandler)
				.failureHandler(oAuth2AuthenticationFailureHandler);
		http.headers().frameOptions().disable();

		// Add our custom Token based authentication filter
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	 
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	// This bean is load the user specific data when form login is used.
	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
