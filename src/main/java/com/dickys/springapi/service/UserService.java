package com.dickys.springapi.service;

import com.dickys.springapi.config.exception.expected.AppException;
import com.dickys.springapi.dto.request.UserRegistrationRequest;
import com.dickys.springapi.entity.User;
import com.dickys.springapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(@NotNull UserRegistrationRequest request) {
        validateUsername(request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user.getId().toString();
    }

    private void validateUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            throw new AppException("Username already exists");
        }
    }
}
