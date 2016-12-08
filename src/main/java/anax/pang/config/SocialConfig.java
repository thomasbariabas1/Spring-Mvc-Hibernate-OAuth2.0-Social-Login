package anax.pang.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import anax.pang.social.AccountConnectionSignUpService;

@Configuration
@EnableSocial
@ComponentScan(basePackages = { "anax.pang.social" })
public class SocialConfig implements SocialConfigurer {

	@Autowired
	private AccountConnectionSignUpService accountConnectionSignUpService;

	@Autowired
	private DataSource dataSource;

	public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment env) {
	//	FacebookConnectionFactory f = new FacebookConnectionFactory(env.getProperty("spring.social.facebook.appId"),env.getProperty("spring.social.facebook.appSecret"));
		FacebookConnectionFactory fcf = new FacebookConnectionFactory(env.getProperty("spring.social.facebook.appId"),
				env.getProperty("spring.social.facebook.appSecret"));
		 fcf.setScope("public_profile,email");
		cfc.addConnectionFactory(fcf);
		cfc.addConnectionFactory(new TwitterConnectionFactory(env.getProperty("spring.social.twitter.appId"),
				env.getProperty("spring.social.twitter.appSecret")));
		

	}

	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource() {			
			@Override
			public String getUserId() {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null) {
					throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
				}
				return authentication.getName();
			}
		};
	}

	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, cfl,
				Encryptors.noOpText());
		repository.setConnectionSignUp(accountConnectionSignUpService);
		return repository;
	}
	
	  @Bean
	    public ConnectController connectController(
	                ConnectionFactoryLocator connectionFactoryLocator,
	                ConnectionRepository connectionRepository) {
	        return new ConnectController(connectionFactoryLocator, connectionRepository);
	    }
	  
}