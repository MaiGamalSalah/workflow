package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.Controllers.AuthController;
import com.example.Mai.sProject.DTO.JWTResponse;
import com.example.Mai.sProject.DTO.LoginRequest;
import com.example.Mai.sProject.DTO.SignupRequest;
import com.example.Mai.sProject.Entites.Role;
import com.example.Mai.sProject.Entites.User;
import com.example.Mai.sProject.Exceptions.InvalidCredentialsExceptions;
import com.example.Mai.sProject.Repositories.UserRepository;
import com.example.Mai.sProject.security.JWT.JWTUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JWTUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JWTUtils jwtUtils,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils=jwtUtils;
        this.authenticationManager=authenticationManager;
    }

    public String registerUser(SignupRequest signupRequest) {
        logger.info("Attempting to register user with username: {}", signupRequest.getUsername());
        // check username
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            logger.warn("Registration failed - Username '{}' is already taken", signupRequest.getUsername());
            throw new RuntimeException("Username is already taken!");
        }

        // check email
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            logger.warn("Registration failed - Email '{}' is already registered", signupRequest.getEmail());
            throw new RuntimeException("Email is already registered!");
        }

        // parse role from request
        Role role;
        try {
            role = Role.valueOf(signupRequest.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid role provided: {}", signupRequest.getRole());
            throw new RuntimeException("Invalid role: " + signupRequest.getRole());
        }

        // create new user
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                role
        );

        userRepository.save(user);
        logger.info("User '{}' registered successfully with role '{}'", signupRequest.getUsername(), role);

        return "User Registered successfully!";
    }

    public JWTResponse login(LoginRequest loginRequest){
        logger.info("Login attempt for username: {}", loginRequest.getUsername());
        try{
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
            );
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            String jwts=jwtUtils.generateToken(userDetails);
            logger.info("Login successful for user: {}", loginRequest.getUsername());

            return new JWTResponse(jwts,userDetails.getUsername(),userDetails.getAuthorities());
        }catch (BadCredentialsException e){
            logger.warn("Invalid login attempt for username: {}", loginRequest.getUsername());
            throw new InvalidCredentialsExceptions("Invalid Credentials");
        }
    }



}
