package com.pc.task_management_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.task_management_system.DTO.AuthDTO.RegisterResponse;
import com.pc.task_management_system.entity.User;
import com.pc.task_management_system.service.UserService;

@RestController
//@PreAuthorize("hasRole('USER')")
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/me")
    public ResponseEntity<RegisterResponse> getUserDetails() {
        User user = userService.getCurrentUserDetails();

        RegisterResponse registerResponse = RegisterResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                .build();

        return ResponseEntity.ok(registerResponse);
    }

    @GetMapping
    public ResponseEntity<List<RegisterResponse>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        List<RegisterResponse> registerResponses = users.stream()
                .map(user -> RegisterResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                        .build())
                .collect(Collectors.toList());

        
        return ResponseEntity.ok(registerResponses);
    }
}
