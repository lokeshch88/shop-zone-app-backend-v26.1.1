package com.shopzone.app.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shopzone.app.filter.JwtFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity //for newwer boot version
@EnableGlobalMethodSecurity(prePostEnabled = true) // older versions
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
	private JwtFilter jwtFilter;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
		.csrf().disable() // <-- CRUCIAL FIX: Ensure this is explicitly called!
    	.cors()
    	.and()
        .csrf().disable()   //csrf disable for ddl reqs
        .authorizeRequests()
        .antMatchers("/user/login", "/user/reset-password", "/user/send-otp", "/user/verify-otp",
     		   "/user/check-availability", "/user/register", "/products/**","/user/test-redis","/user/delivery-partners"
     		   ,"/category/get-all","/products-images/*"
     		   , "/api/merchants/*", "/store/**", "/product-images/**", "/address/fetch-pincode"
     		   ).permitAll() // allow endpoints without auth
        
        .anyRequest().authenticated() // protect all other endpoints
//        .and()
//       .httpBasic() // enables basic auth (username/password prompt)
       .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        ;
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

	 
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//for allowing patch reqs and its options req
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	     // Replaced generic domains with your exact production S3 endpoint and AWS Elastic IP
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://192.168.3.118:3000",
            "http://32.236.64.240", // <- Main Backend EC2 Elastic IP
            "http://shopzone-web-frontend-2026.s3-website-ap-southeast-2.amazonaws.com" // <-full S3 website endpoint here
        ));
        
        //  for all REST verbs including PATCH and pre-flight OPTIONS handshakes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Keep as false since we explicitly listed the origin strings
        configuration.setAllowCredentials(false); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}
}     
