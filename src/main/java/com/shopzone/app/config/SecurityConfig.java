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
    	.cors()
    	.and()
        .csrf().disable()   //csrf disable for ddl reqs
        .authorizeRequests()
        .antMatchers("/user/login", "/user/reset-password", "/user/send-otp", "/user/verify-otp",
     		   "/user/check-availability", "/user/register", "/products/all", "/products/{productId}", "/products/category/{category}","/user/test-redis","/user/delivery-partners"
     		   ,"/category/get-all","/products-images/*").permitAll() // allow login endpoint without auth
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
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://192.168.3.110:3000" ));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS", "PUT"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}     
