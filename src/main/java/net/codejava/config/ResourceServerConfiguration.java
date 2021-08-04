package net.codejava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource-server-rest-api";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http
    	.authorizeRequests().antMatchers(HttpMethod.GET,"/registration").hasAuthority("ADMIN").and()
                .authorizeRequests().antMatchers("/","/login").permitAll()
    	//.and().authorizeRequests().antMatchers(HttpMethod.POST,"/user").permitAll()
         .and().authorizeRequests().antMatchers("/index","/new","/edit/*","/delete/*").authenticated();
        //.anyRequest().authenticated();
    	

    }
}

