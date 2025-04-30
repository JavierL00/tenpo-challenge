package com.tenpo.api.configuration.web;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CachedBodyHttpServletResponseTest {

	private HttpServletResponse originalResponse;

	@BeforeEach
	void setUp() {
		originalResponse = mock(HttpServletResponse.class);
	}

	@Test
	void testGetOutputStream_writesToCache() throws Exception {
		CachedBodyHttpServletResponse response = new CachedBodyHttpServletResponse(originalResponse);

		ServletOutputStream out = response.getOutputStream();
		String content = "stream body test";

		out.write(content.getBytes());
		out.flush();

		String result = new String(response.getCachedBody());
		assertEquals(content, result);
	}

	@Test
	void testGetWriter_writesToCache() {
		CachedBodyHttpServletResponse response = new CachedBodyHttpServletResponse(originalResponse);

		PrintWriter writer = response.getWriter();
		String content = "writer body test";

		writer.write(content);
		writer.flush();

		String result = new String(response.getCachedBody());
		assertEquals(content, result);
	}

	@Test
	void testGetCachedBody_multipleWrites() {
		CachedBodyHttpServletResponse response = new CachedBodyHttpServletResponse(originalResponse);

		PrintWriter writer = response.getWriter();
		writer.write("part1-");
		writer.write("part2");
		writer.flush();

		assertEquals("part1-part2", new String(response.getCachedBody()));
	}
}
