package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

@Configuration
class CustomFilterConfiguration {

	// <.>
	@Bean
	@Order(Ordered.LOWEST_PRECEDENCE)
	FilterRegistrationBean<MyFilter> myFilterFilterRegistrationBean() {
		var myFilter = new MyFilter();
		var frb = new FilterRegistrationBean<>(myFilter);
		frb.addUrlPatterns("/test", "/test/*");
		frb.setAsyncSupported(true);
		return frb;
	}

	// <.>
	static class MyFilter extends HttpFilter {

		@Override
		protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
				throws IOException, ServletException {

			IO.println("before " + request.getRequestURL());
			// <.>
			if (request.getUserPrincipal() == null) {
				// <.>
				chain.doFilter(request, response);
			} //
			else {
				response.setContentType(MediaType.TEXT_HTML_VALUE);
				response.setStatus(HttpStatus.OK.value());
				var userPrincipal = request.getUserPrincipal();
				response.getWriter().write("""
						<html>
						 <body>
						    <h1>
						     Hello, %s!
						    </h1>
						 </body>
						</html>
						""".formatted(userPrincipal.getName()));

			}
			IO.println("after " + request.getRequestURL());
		}

	}

}
