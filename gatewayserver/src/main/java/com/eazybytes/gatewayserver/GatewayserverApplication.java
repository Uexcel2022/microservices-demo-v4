package com.eazybytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator eazyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {

          return routeLocatorBuilder.routes()
				  .route(p-> p.path("/eazybank/accounts/**")
						  .filters(
								  f->f.rewritePath(
										  "/eazybank/accounts/(?<segment>.*)","/${segment}")
										  .addResponseHeader("X-Response-Time",formatDate(new Date()))

						  )
						  .uri("lb://ACCOUNTS"))


				  .route(p-> p.path("/eazybank/loan/**")
						  .filters(
								  f->f.rewritePath(
										  "/eazybank/loan/(?<segment>.*)","/${segment}")
										  .addResponseHeader("X-Response-Time",formatDate(new Date()))
						  )
						  .uri("lb://LOAN"))


				  .route(p-> p.path("/eazybank/card/**")
						  .filters(
								  f->f.rewritePath(
										  "eazybank/card/(?<segment>.*)","/${segment}")
										  .addResponseHeader("X-Response-Time",formatDate(new Date()))
						  )
						  .uri("lb://CARD"))

				  .build();
	}

	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		return sdf.format(date);
	}

}
