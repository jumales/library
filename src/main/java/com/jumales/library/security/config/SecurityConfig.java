package com.jumales.library.security.config;

import com.jumales.library.security.jwt.JwtSecurityConfigurer;
import com.jumales.library.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${rest.root.url}")
    private String api;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/auth/signin").permitAll()
                .antMatchers(HttpMethod.GET, api + "/books/**").permitAll()
                .antMatchers(HttpMethod.GET, api + "/books/**").permitAll()
                .antMatchers(HttpMethod.PUT, api + "/authors/**").permitAll()
                .antMatchers(HttpMethod.PUT, api + "/authors/**").permitAll()
                .antMatchers(HttpMethod.POST, api + "/books/**").permitAll()
                .antMatchers(HttpMethod.POST, api + "/authors/**").permitAll()
                .antMatchers(HttpMethod.DELETE, api + "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, api + "/authors/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
        //@formatter:on
    }
}