package com.tenpo.api.call.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallHistoryDtoResponse {
	private Long id;
	private LocalDateTime timestamp;
	private String endpoint;
	private String requestParams;
	private String responseData;
	private boolean error;
}
