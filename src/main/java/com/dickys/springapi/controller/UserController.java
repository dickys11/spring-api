package com.dickys.springapi.controller;

import com.dickys.springapi.config.controller.BaseController;
import com.dickys.springapi.config.controller.ResultResponse;
import com.dickys.springapi.dto.request.UserRegistrationRequest;
import com.dickys.springapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResultResponse<Object>> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return generateResponse(userService.registerUser(request)).done("Success", HttpStatus.CREATED);
    }
}
