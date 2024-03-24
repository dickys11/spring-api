package com.dickys.springapi.service;

import com.dickys.springapi.config.exception.expected.AppException;
import com.dickys.springapi.dto.request.UserRegistrationRequest;
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

    public String registerUser(@NotNull UserRegistrationRequest request) {
        validateUsername(request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(hashPassword(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user.getId().toString();
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
