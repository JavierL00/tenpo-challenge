package com.tenpo.api.call.dto;

public record CallHistoryDtoRequest(String endpoint, String requestParams, String responseData, boolean isError) {
}
