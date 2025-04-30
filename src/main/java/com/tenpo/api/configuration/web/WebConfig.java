package com.tenpo.api.configuration.web;

import com.tenpo.api.configuration.ratelimit.RateLimitFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final RequestLoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	}

	@Bean
	public Filter cachedBodyFilter() {
		return (request, response, chain) -> {
			var wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
			var wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

			chain.doFilter(wrappedRequest, wrappedResponse);

			response.getOutputStream().write(wrappedResponse.getCachedBody());
		};
	}

	@Bean
	public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration(RateLimitFilter filter) {
		FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		return registration;
	}
}
