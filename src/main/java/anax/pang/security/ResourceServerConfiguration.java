package anax.pang.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.social.security.SpringSocialConfigurer;
 
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
 
    private static final String RESOURCE_ID = "pang_api";
     
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
    }
 
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	http
    	      .formLogin()
    	      	.loginPage("/signin")
    	      	.loginProcessingUrl("/signin/authenticate")
    	      	.failureUrl("/signin?param.error=bad_credentials")
    	     .and()
    	     	.logout()
    	     		.logoutUrl("/signout")
    	     			.deleteCookies("JSESSIONID")
    	     	.and()
    		.authorizeRequests()
    		    .antMatchers("/auth/**")
    		    	.permitAll()
	    		.antMatchers("/index.jsp")
	    			.permitAll()
	    		.antMatchers("/register/**")
	    			.permitAll()
	    		.antMatchers("/api/user0/**")
	    			.hasAnyRole("USER","ADMIN")
	    		.antMatchers("/api/**")
	    			.hasRole("ADMIN")
	    				
	    		.and()	    		
	    		.apply(new SpringSocialConfigurer());
    }
 
}