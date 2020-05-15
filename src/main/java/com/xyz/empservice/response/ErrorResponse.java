package com.xyz.empservice.response;

import lombok.Data;

@Data
public class ErrorResponse {
private String errorMessage;
private String requestedURL;

}
