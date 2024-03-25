package com.dickys.springapi.controller;

import com.dickys.springapi.config.controller.BaseController;
import com.dickys.springapi.config.controller.ResultResponse;
import com.dickys.springapi.dto.request.UserLoginRequest;
import com.dickys.springapi.dto.request.UserRegistrationRequest;
import com.dickys.springapi.dto.response.UserLoginResponse;
import com.dickys.springapi.dto.response.UserRegisterResponse;
import com.dickys.springapi.enums.MessagesId;
import com.dickys.springapi.service.UserService;
import com.dickys.springapi.utils.MessageUtils;
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

    private final UserService userService;
    private final MessageUtils messageUtils;

    @Autowired
    public UserController(UserService userService, MessageUtils messageUtils) {
        this.userService = userService;
        this.messageUtils = messageUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<ResultResponse<UserRegisterResponse>> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return generateResponse(userService.registerUser(request),
                messageUtils.getMessage(MessagesId.SUCCESS_MESSAGE.getId()),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResultResponse<UserLoginResponse>> loginUser(@Valid @RequestBody UserLoginRequest request) {
        return generateResponse(userService.loginUser(request),
                messageUtils.getMessage(MessagesId.SUCCESS_MESSAGE.getId()),
                HttpStatus.OK);
    }
}
