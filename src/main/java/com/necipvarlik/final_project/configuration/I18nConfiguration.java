package com.necipvarlik.final_project.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class I18nConfiguration implements WebMvcConfigurer {

	@Bean
	public MessageSource messageSource() {

		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

		messageSource.setBasename("static/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(10);

		return messageSource;
	}
}
