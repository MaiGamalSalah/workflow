package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.DTO.JWTResponse;
import com.example.Mai.sProject.DTO.LoginRequest;
import com.example.Mai.sProject.DTO.SignupRequest;
import com.example.Mai.sProject.Entites.Role;
import com.example.Mai.sProject.Entites.User;
import com.example.Mai.sProject.Repositories.UserRepository;
import com.example.Mai.sProject.security.JWT.JWTUtils;
import com.example.Mai.sProject.user_config.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   //load
class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest(
                "testuser",
                "test@example.com",
                "password",
                "USER"
        );
    }

    @Test
    void register_successful_user() {
        // Arrange
        when(repository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
        when(repository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encoded password");
        when(repository.save(any(User.class))).thenReturn(
                new User("testuser", "test@example.com", "encoded password", Role.USER)
        );

        // Act
        String result = authService.registerUser(signupRequest);

        // Assert
        assertEquals("User Registered successfully!", result);

        verify(repository).existsByUsername(signupRequest.getUsername());
        verify(repository).existsByEmail(signupRequest.getEmail());
        verify(passwordEncoder).encode(signupRequest.getPassword());
        verify(repository).save(any(User.class));
    }
    @Test
    void login_successful_user() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password");

        User user = new User("testuser", "test@example.com", "encoded password", Role.USER);
        UserInfo userInfo = new UserInfo(user);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userInfo);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtUtils.generateToken(userInfo)).thenReturn("mocked-jwt");

        // Act
        JWTResponse response = authService.login(loginRequest);

        // Assert
        assertEquals("mocked-jwt", response.getToken());
        assertEquals("testuser", response.getUsername());
        assertEquals(1, response.getAuthorities().size());
        assertEquals("ROLE_USER", response.getAuthorities().iterator().next().getAuthority());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(userInfo);
    }

}
