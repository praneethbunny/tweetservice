package com.tweetapp.tweetservice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthorizationEntryPoint jwtAuthorizationEntryPoint;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.cors().configurationSource(request -> {
    		CorsConfiguration cors = new CorsConfiguration();
			cors.setAllowedOrigins(Arrays.asList("*"
					));
			cors.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
			cors.setAllowedHeaders(Arrays.asList("*"));
			return cors;
		}).and().csrf().disable()
                    .authorizeRequests().antMatchers("/api/v1.0/tweets/health-check").permitAll()
                    .anyRequest().authenticated().and()
                    .addFilter(new AuthorizationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(jwtAuthorizationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}