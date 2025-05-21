package com.pc.task_management_system.controller;

import com.pc.task_management_system.DTO.AuthDTO.AuthResponse;
import com.pc.task_management_system.DTO.AuthDTO.LoginRequest;
import com.pc.task_management_system.DTO.AuthDTO.RegisterRequest;
import com.pc.task_management_system.DTO.AuthDTO.RegisterResponse;
import com.pc.task_management_system.entity.Role;
import com.pc.task_management_system.entity.User;
import com.pc.task_management_system.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        log.info("Registration request for "+ registerRequest.getUsername());
        User user = authService.register(registerRequest);

        RegisterResponse registerResponse = RegisterResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        log.info("Login Request for {}", request.getUsername());

        String token = authService.login(request);

        log.info("Login success for {}, sending token.", request.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
