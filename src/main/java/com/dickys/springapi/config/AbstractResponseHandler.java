package com.dickys.springapi.config;

import org.apache.el.util.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseHandler {

    private final boolean isDevelopment = true;

    public abstract Object data();

    public ResponseEntity<ResultResponse<Object>> done(String msg, HttpStatus httpStatus) {
        Object processResponse = data();
        if (processResponse instanceof Exception) {
            return onError(msg, (Exception) processResponse, httpStatus);
        } else {
            return onSuccess(msg, processResponse, httpStatus);
        }
    }

    private ResponseEntity<ResultResponse<Object>> onError(String msg, Exception ex, HttpStatus httpStatus) {
        String debugInfo = isDevelopment ? ex.getStackTrace()[0].toString() :null;

        MetaResponse metaResponse = new MetaResponse(httpStatus.value(), msg, debugInfo);

        ResultResponse<Object> result = new ResultResponse<>("ERROR", null, metaResponse);
        return generateResponseEntity(result, httpStatus);
    }

    private ResponseEntity<ResultResponse<Object>> onSuccess(String msg, Object any, HttpStatus httpStatus) {
        MetaResponse metaResponse = new MetaResponse(httpStatus.value(), msg, null);

        String status = HttpStatus.valueOf(metaResponse.getCode()).is2xxSuccessful() ? "OK" : "ERROR";
        ResultResponse<Object> result = new ResultResponse<>(status, any, metaResponse);
        return generateResponseEntity(result, httpStatus);
    }

    private ResponseEntity<ResultResponse<Object>> generateResponseEntity(ResultResponse<Object> result, HttpStatus code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(result, headers, code);
    }
}
