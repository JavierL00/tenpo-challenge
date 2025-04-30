package com.tenpo.api.configuration.web;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CachedBodyHttpServletRequestTest {

	private static final String MOCK_BODY = "{\"num1\":10,\"num2\":20}";

	private HttpServletRequest originalRequest;

	@BeforeEach
	void setUp() throws IOException {
		originalRequest = mock(HttpServletRequest.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(MOCK_BODY.getBytes());
		when(originalRequest.getInputStream()).thenReturn(new DelegatingServletInputStream(inputStream));
	}

	@Test
	void testGetInputStream_returnsCorrectContent() throws IOException {
		CachedBodyHttpServletRequest wrapper = new CachedBodyHttpServletRequest(originalRequest);

		String content1 = new String(wrapper.getInputStream().readAllBytes());
		String content2 = new String(wrapper.getInputStream().readAllBytes());

		assertEquals(MOCK_BODY, content1);
		assertEquals(MOCK_BODY, content2);
	}

	@Test
	void testGetReader_returnsCorrectContent() throws IOException {
		CachedBodyHttpServletRequest wrapper = new CachedBodyHttpServletRequest(originalRequest);

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = wrapper.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}

		assertEquals(MOCK_BODY, sb.toString());
	}

	@Test
	void testServletInputStreamMethods_areCovered() throws Exception {
		HttpServletRequest original = mock(HttpServletRequest.class);
		when(original.getInputStream()).thenReturn(new CachedBodyHttpServletRequestTest.DelegatingServletInputStream(new ByteArrayInputStream("test".getBytes())));

		CachedBodyHttpServletRequest wrapper = new CachedBodyHttpServletRequest(original);
		ServletInputStream inputStream = wrapper.getInputStream();

		assertTrue(inputStream.isReady());
		assertFalse(inputStream.isFinished());
		inputStream.setReadListener(null);

		int totalBytes = 0;
		while (!inputStream.isFinished()) {
			totalBytes += inputStream.read();
		}

		assertTrue(totalBytes > 0);
	}

	static class DelegatingServletInputStream extends ServletInputStream {
		private final InputStream stream;

		public DelegatingServletInputStream(InputStream stream) {
			this.stream = stream;
		}

		@Override
		public int read() throws IOException {
			return stream.read();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(jakarta.servlet.ReadListener readListener) {
		}
	}
}
