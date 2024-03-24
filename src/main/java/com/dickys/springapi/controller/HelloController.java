package com.dickys.springapi.controller;

import com.dickys.springapi.config.BaseController;
import com.dickys.springapi.config.ResultResponse;
import com.dickys.springapi.config.exception.expected.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController {

    @GetMapping("/")
    public ResponseEntity<ResultResponse<Object>> index() {
        return generateResponse("Hello World!").done("Success", HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<ResultResponse<Object>> error() {
        throw new AppException("Error Message");
    }
}
