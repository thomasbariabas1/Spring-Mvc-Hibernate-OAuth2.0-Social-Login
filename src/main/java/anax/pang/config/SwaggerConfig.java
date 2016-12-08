package anax.pang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(
				        Lists.newArrayList(new ParameterBuilder()
				            .name("Authorization")
				            .description("Access token")
				            .modelRef(new ModelRef("string"))
				            .parameterType("header")
				            .required(true)
				            .build()))
					.groupName("api")
						.select()
							.apis(RequestHandlerSelectors.any())
							.paths(PathSelectors.regex("/api/.*"))
							.build()
						.apiInfo(apiInfo());
	}
	
	@Bean
	public Docket register() {
		return new Docket(DocumentationType.SWAGGER_2)
					.groupName("register")
						.select()
							.apis(RequestHandlerSelectors.any())
							.paths(PathSelectors.regex("/register"))
							.build()
						.apiInfo(registerInfo());
	}
	
	@Bean
	public Docket oauthToken() {
		return new Docket(DocumentationType.SWAGGER_2)
					.groupName("oauth")
						.apiInfo(oauthTokenInfo());
		
	}
	
	@Bean
    SecurityConfiguration security() { 
		return new SecurityConfiguration(	"pang-client",
											"pangword",
											"pangOAuth2Realm",
											"Pang",
											"Basic cGFuZy1jbGllbnQ6cGFuZ3dvcmQ",
											ApiKeyVehicle.HEADER, 
											"Authorization", 
											",");
    }

	// API info
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
						.title("Pang API")
						.description("Mini games platform API. Requires basic user or admin authorization.")
						.version("0.1")
						.termsOfServiceUrl("https://github.com/tofis/pang_backend")
						.build();
	}
	
	private ApiInfo registerInfo() {
		return new ApiInfoBuilder()
						.title("Pang registration")
						.description("User registration. Requires no authorization. Default basic user role registration.")
						.version("0.1")
						.termsOfServiceUrl("https://github.com/tofis/pang_backend")
						.build();
	}
	
	private ApiInfo oauthTokenInfo() {
		return new ApiInfoBuilder()
						.title("Pang authentication module")
						.description("User authentication. Grant only type is password(email and password).")
						.version("0.1")
						.termsOfServiceUrl("https://github.com/tofis/pang_backend")
						.build();
	}
}
