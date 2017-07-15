package com.marcnuri.projectreactordemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan({
	"com.marcnuri.projectreactordemo",
	"com.marcnuri.projectreactordemo.rest"})
public class ProjectreactordemoApplication {

	private static final Logger LOGGER = Logger.getLogger(ProjectreactordemoApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(ProjectreactordemoApplication.class, args);
	}

	@Bean(name = "globalRestTemplate")
	public RestTemplate getGlobalRestTemplate() {
		RestTemplate restClient = new RestTemplate(
				new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		// Add additional supported types (Necessary for Coindesk API)
		restClient.getMessageConverters().stream()
				.filter(c->c instanceof MappingJackson2HttpMessageConverter)
				.forEach(c-> {
					final MappingJackson2HttpMessageConverter mj = ((MappingJackson2HttpMessageConverter) c);
					final List<MediaType> supportedTypes = new ArrayList<>(mj.getSupportedMediaTypes());
					supportedTypes.add(MediaType.valueOf("application/javascript"));
					mj.setSupportedMediaTypes(supportedTypes);
				});
//		// Add one interceptor like in your example, except using anonymous class.
//		restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {
//			LOGGER.info("Intercepting...");
//			return execution.execute(request, body);
//		}));
		return restClient;
	}

}
