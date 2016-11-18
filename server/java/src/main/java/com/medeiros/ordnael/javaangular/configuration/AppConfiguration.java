package com.medeiros.ordnael.javaangular.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.medeiros.ordnael.javaangular.controller")
public class AppConfiguration extends WebMvcConfigurerAdapter {

	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				// .exposedHeaders("header1", "header2")
				.allowCredentials(false).maxAge(3600);
		
		
	}
	
}
