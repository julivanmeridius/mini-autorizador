package br.com.vr.autorizador.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.vr.autorizador.util.AutorizadorConstants;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings("deprecation")
@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
public class SwaggerConfig {

	private final ResponseMessage m200 = simpleMessage(200, AutorizadorConstants.RESPONSE_200);
	private final ResponseMessage m201 = simpleMessage(201, AutorizadorConstants.RESPONSE_201);
	private final ResponseMessage m204 = simpleMessage(204, AutorizadorConstants.RESPONSE_204);

	private final ResponseMessage m400 = simpleMessage(400, AutorizadorConstants.RESPONSE_400);
	private final ResponseMessage m401 = simpleMessage(401, AutorizadorConstants.RESPONSE_401);
	private final ResponseMessage m404 = simpleMessage(404, AutorizadorConstants.RESPONSE_404);

	private final ResponseMessage m500 = simpleMessage(500, AutorizadorConstants.RESPONSE_500);

	private static final String RESOURCE_PACKAGE = "br.com.vr.autorizador.resource";

	@Value("${swagger.info.title}")
	private String title;

	@Value("${swagger.info.description}")
	private String description;

	@Value("${swagger.info.version}")
	private String version;

	@Value("${swagger.info.termsOfServiceUrl}")
	private String termsOfServiceUrl;

	@Value("${swagger.info.contactName}")
	private String contactName;

	@Value("${swagger.info.contactUrl}")
	private String contactUrl;

	@Value("${swagger.info.contactEmail}")
	private String contactEmail;

	@Value("${swagger.info.license}")
	private String license;

	@Value("${swagger.info.licenseUrl}")
	private String licenseUrl;

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m200, m400, m401, m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m401, m404, m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m200, m204, m401, m400, m404, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204, m400, m401, m404, m500))
				.select()
				.apis(RequestHandlerSelectors.basePackage(RESOURCE_PACKAGE))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.produces(DEFAULT_PRODUCES_AND_CONSUMES);
	}

	private ResponseMessage simpleMessage(final int code, final String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(title, description, version, termsOfServiceUrl,
				new Contact(contactName, contactUrl, contactEmail), license, licenseUrl, Collections.emptyList());
	}
}
