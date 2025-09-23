package com.example.Mai.sProject.Controllers;

import com.example.Mai.sProject.DTO.JWTResponse;
import com.example.Mai.sProject.DTO.LoginRequest;

import com.example.Mai.sProject.Entites.Role;
import com.example.Mai.sProject.Entites.User;
import com.example.Mai.sProject.user_config.UserInfo;
import com.example.Mai.sProject.security.JWT.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
import com.example.Mai.sProject.DTO.SignupRequest;
import com.example.Mai.sProject.Entites.Role;
import com.example.Mai.sProject.Entites.User;
import com.example.Mai.sProject.Repositories.UserRepository;
import com.example.Mai.sProject.Services.AuthService;
import com.example.Mai.sProject.security.JWT.JWTUtils;
import com.example.Mai.sProject.user_config.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {


    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequest req;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserInfo userInfo;
    @BeforeEach
    void setup1() {
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "test@example.com", "encodedPassword", Role.USER);
        userInfo = new UserInfo(user);
    }

    @Test
    void login_successful_user() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userInfo);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateToken(userInfo)).thenReturn("mocked-jwt");

        JWTResponse response = authService.login(loginRequest);


        assertEquals("mocked-jwt", response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals(1, response.getAuthorities().size());
        assertEquals("ROLE_USER", response.getAuthorities().iterator().next().getAuthority());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(userInfo);
    }

    @BeforeEach
    void setup() {
        req = new SignupRequest();
        req.setUsername("newUser");
        req.setEmail("newUser@example.com");
        req.setPassword("password123");
        req.setRole("USER");
    }

    @Test
    void registerUser_ShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Registered successfully!"));
    }

//    @Test
//    void registerUser_WhenUsernameAlreadyTaken_ShouldReturnConflict() throws Exception {
//
//
//
//
//        mockMvc.perform(post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isConflict())
//                .andExpect(content().string("Username is already taken!"));
//    }


    @AfterEach
    void destroy() {
        repository.deleteAll();
    }
}
