package anax.pang.security;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
 
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@ComponentScan(basePackages = {"anax.pang.security"})
public class OAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	Securityhandler successHandler ;
	
	@Autowired
	    private AuthorizationServerTokenServices authTokenServices;
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	DataSource dataSource;

	 @Autowired
	  OAuth2ClientContext oauth2ClientContext;
	
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.jdbcAuthentication()
			.dataSource(dataSource)
				.usersByUsernameQuery("select email as Username,password,1 from user where email=? ")
				.authoritiesByUsernameQuery("select user.email as Username, role.name from role,user where user.email=?")
					;
                

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SpringSocialConfigurer socialCfg = new SpringSocialConfigurer();
        socialCfg
            .addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
                @SuppressWarnings("unchecked")
                public SocialAuthenticationFilter postProcess(SocialAuthenticationFilter filter){
                    filter.setAuthenticationSuccessHandler(
                            new SocialAuthenticationSuccessHandler(
                                    authTokenServices,
                                    "pang-client"
                            )
                        );
                    return filter;
                }
            });
		
		http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/oauth/token").permitAll() .antMatchers("/auth/**").permitAll().and()
		.apply(socialCfg);
		}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}
	 @Bean
     public SocialUserDetailsService socialUsersDetailService() {
	        return new SimpleSocialUsersDetailService(userDetailsService());
     }
	 
     
}