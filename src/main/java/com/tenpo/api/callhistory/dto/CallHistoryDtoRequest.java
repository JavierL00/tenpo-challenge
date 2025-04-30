package com.tenpo.api.callhistory.dto;

public record CallHistoryDtoRequest(String endpoint, String requestParams, String responseData, boolean isError) {
}
