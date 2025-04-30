package com.tenpo.api.configuration.web;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

	private final ByteArrayOutputStream cachedContent = new ByteArrayOutputStream();
	private final ServletOutputStream outputStream = new ServletOutputStream() {
		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
		}

		@Override
		public void write(int b) throws IOException {
			cachedContent.write(b);
		}
	};
	private final PrintWriter writer = new PrintWriter(cachedContent);

	public CachedBodyHttpServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return outputStream;
	}

	@Override
	public PrintWriter getWriter() {
		return writer;
	}

	public byte[] getCachedBody() {
		writer.flush();
		return cachedContent.toByteArray();
	}
}
