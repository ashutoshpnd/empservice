package com.xyz.empservice.response;

import lombok.Data;

@Data
public class ExceptionResponse {
private String errorMessage;
private String requestedURL;

}
