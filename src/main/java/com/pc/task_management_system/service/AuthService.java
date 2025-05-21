package com.pc.task_management_system.service;

import com.pc.task_management_system.DTO.AuthDTO.LoginRequest;
import com.pc.task_management_system.DTO.AuthDTO.RegisterRequest;
import com.pc.task_management_system.entity.Role;
import com.pc.task_management_system.entity.User;
import com.pc.task_management_system.exception.EmailIdAlreadyExistsException;
import com.pc.task_management_system.exception.UsernameAlreadyExistsException;
import com.pc.task_management_system.exception.UsernameNotFoundException;
import com.pc.task_management_system.repository.RoleRepository;
import com.pc.task_management_system.repository.UserRepository;
import com.pc.task_management_system.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil, RoleRepository roleRepository, AuthenticationManager authenticationManager){
        this.userRepository=userRepository;
        this.passwordEncoder=bCryptPasswordEncoder;
        this.jwtUtil=jwtUtil;
        this.roleRepository=roleRepository;
        this.authenticationManager=authenticationManager;
    }

    public User register(RegisterRequest registerRequest){

        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UsernameAlreadyExistsException();
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailIdAlreadyExistsException();
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));


        return userRepository.save(user);

    }
/*
    public String login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UsernameNotFoundException::new);

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtUtil.generateToken(user);
    }
*/
    public String login(LoginRequest request){
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authentication.isAuthenticated()){
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(UsernameNotFoundException::new);
            return jwtUtil.generateToken(user);
        } else {
            throw new UsernameNotFoundException();
        }
    }

}
