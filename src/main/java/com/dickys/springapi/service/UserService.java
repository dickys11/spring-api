package com.dickys.springapi.service;

import com.dickys.springapi.config.exception.expected.AppException;
import com.dickys.springapi.dto.request.UserLoginRequest;
import com.dickys.springapi.dto.request.UserRegistrationRequest;
import com.dickys.springapi.dto.response.UserLoginResponse;
import com.dickys.springapi.dto.response.UserRegisterResponse;
import com.dickys.springapi.entity.User;
import com.dickys.springapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponse registerUser(@NotNull UserRegistrationRequest request) {
        validateUsername(request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new UserRegisterResponse(user.getId(), user.getUsername());
    }

    public UserLoginResponse loginUser(@NotNull UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new AppException("Username not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException("Wrong password");
        }

        return new UserLoginResponse(user.getUsername());
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AppException("Username already exists");
        }
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
