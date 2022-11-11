package com.niit.ApiGateway;

import com.niit.ApiGateway.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@Configuration
@EnableEurekaClient
@CrossOrigin(origins="*")
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return   builder.routes()
			 .route(p -> p.path("/api/v1/**").uri("lb://user-authentication-service"))
			 .route(p -> p.path("/api/v2/**").uri("lb://kanban-service"))
			//	.route(p -> p.path("/api/v1/**").uri("http://localhost:8081"))
			//	.route(p -> p.path("/api/v2/**").uri("http://localhost:8082"))
				.build();
	}

	@Bean
	public FilterRegistrationBean jwtFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new JwtFilter());
		filterRegistrationBean.addUrlPatterns("/api/v2/*");
		return filterRegistrationBean;
	}
}
