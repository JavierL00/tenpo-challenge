package com.tenpo.api.configuration.web;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebConfigTest {

	@Test
	void testCachedBodyFilter_wrapsRequestAndResponse() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		ByteArrayOutputStream capturedResponseBody = new ByteArrayOutputStream();
		ServletOutputStream servletOutputStream = new ServletOutputStream() {
			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setWriteListener(jakarta.servlet.WriteListener writeListener) {
			}

			@Override
			public void write(int b) {
				capturedResponseBody.write(b);
			}
		};

		when(request.getInputStream()).thenReturn(new CachedBodyHttpServletRequestTest.DelegatingServletInputStream(new ByteArrayInputStream("{}".getBytes())));
		when(response.getOutputStream()).thenReturn(servletOutputStream);

		WebConfig config = new WebConfig(null);
		Filter filter = config.cachedBodyFilter();

		filter.doFilter(request, response, (req, res) -> {
			assertInstanceOf(CachedBodyHttpServletRequest.class, req);
			assertInstanceOf(CachedBodyHttpServletResponse.class, res);

			CachedBodyHttpServletResponse wrappedResponse = (CachedBodyHttpServletResponse) res;

			PrintWriter writer = wrappedResponse.getWriter();
			writer.write("wrapped test");
			writer.flush();
		});

		assertEquals("wrapped test", capturedResponseBody.toString());
	}
}
