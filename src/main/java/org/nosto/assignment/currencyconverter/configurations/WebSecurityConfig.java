package org.nosto.assignment.currencyconverter.configurations;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.nosto.assignment.currencyconverter.constants.APIConstants.BASE_API_PATH;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, BASE_API_PATH + "/**").permitAll().and()
                .headers().contentSecurityPolicy("script-src 'self'");
    }
}
