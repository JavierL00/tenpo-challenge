package com.tenpo.api.configuration.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

	private static final Integer NUMBER_PETITIONS = 5;
	private static final Integer MINUTES_DURATION = 1;

	private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

	private Bucket createNewBucket() {
		Bandwidth limit =
		 Bandwidth.classic(NUMBER_PETITIONS, Refill.intervally(NUMBER_PETITIONS, Duration.ofMinutes(MINUTES_DURATION)));
		return Bucket.builder().addLimit(limit).build();
	}

	@Override
	public void doFilter(
	 ServletRequest request, ServletResponse response,
	 FilterChain chain
	) throws IOException, ServletException {

		String ip = request.getRemoteAddr();
		Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

		if (bucket.tryConsume(1)) {
			chain.doFilter(request, response);
		}
		else {
			((HttpServletResponse) response).setStatus(429);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"Rate limit exceeded\"}");
		}
	}
}
