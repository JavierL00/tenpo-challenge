package com.tenpo.api.configuration.web;

import com.tenpo.api.callhistory.service.ICallHistoryService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestLoggingInterceptorTest {

	private ICallHistoryService callHistoryService;
	private RequestLoggingInterceptor interceptor;

	@BeforeEach
	void setUp() {
		callHistoryService = mock(ICallHistoryService.class);
		interceptor = new RequestLoggingInterceptor(callHistoryService);
	}

	@Test
	void testAfterCompletion_successfulRequest_logsHistory() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/calculate");
		when(request.getInputStream()).thenReturn(new DelegatingServletInputStream("{\"num1\":10,\"num2\":5}".getBytes()));
		when(response.getStatus()).thenReturn(200);

		interceptor.afterCompletion(request, response, null, null);

		verify(callHistoryService, times(1)).saveHistory(argThat(history -> history.endpoint().equals("/calculate") &&
		 history.requestParams().contains("num1") && history.responseData() == null && !history.isError()));
	}

	@Test
	void testAfterCompletion_errorRequest_logsErrorResponse() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/fail");
		when(request.getInputStream()).thenReturn(new DelegatingServletInputStream("{}".getBytes()));
		when(response.getStatus()).thenReturn(500);

		Exception ex = new RuntimeException("Service failure");

		interceptor.afterCompletion(request, response, null, ex);

		verify(callHistoryService, times(1)).saveHistory(argThat(history -> history.endpoint().equals("/fail") &&
		 history.responseData().equals("Service failure") && history.isError()));
	}

	@Test
	void testAfterCompletion_withCachedResponse_readsResponseBody() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		CachedBodyHttpServletResponse response = mock(CachedBodyHttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/cached");
		when(request.getInputStream()).thenReturn(new DelegatingServletInputStream("{}".getBytes()));
		when(response.getStatus()).thenReturn(200);
		when(response.getCachedBody()).thenReturn("mocked response".getBytes());

		interceptor.afterCompletion(request, response, null, null);

		verify(callHistoryService).saveHistory(argThat(history -> history.endpoint().equals("/cached") &&
		 history.responseData().equals("mocked response") && !history.isError()));
	}

	@Test
	void testAfterCompletion_throwsIOException_isCaught() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(request.getRequestURI()).thenReturn("/io-error");
		when(request.getInputStream()).thenThrow(new IOException("Simulated IO error"));
		when(response.getStatus()).thenReturn(200);

		assertDoesNotThrow(() -> interceptor.afterCompletion(request, response, null, null));
	}

	@Test
	void testGetResponseBody_variants() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
		assertEquals("resp", callGetResponseBody(null, "resp", false));

		assertEquals("Error", callGetResponseBody(null, "ignored", true));

		Exception ex = new RuntimeException("Something went wrong");
		assertEquals("Something went wrong", callGetResponseBody(ex, "ignored", true));
	}

	private String callGetResponseBody(Exception ex, String body, boolean isError) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		return interceptor.getClass().getDeclaredMethod("getResponseBody", Exception.class, String.class, boolean.class)
		 .invoke(interceptor, ex, body, isError).toString();
	}


	static class DelegatingServletInputStream extends ServletInputStream {
		private final InputStream sourceStream;

		public DelegatingServletInputStream(byte[] data) {
			this.sourceStream = new ByteArrayInputStream(data);
		}

		@Override
		public int read() throws IOException {
			return sourceStream.read();
		}

		@Override
		public boolean isFinished() {
			try {
				return sourceStream.available() == 0;
			}
			catch (IOException e) {
				return true;
			}
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
