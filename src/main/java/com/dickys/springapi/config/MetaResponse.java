package com.dickys.springapi.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetaResponse {
    private int code;
    private String message;
    private String debugMsg;
}
