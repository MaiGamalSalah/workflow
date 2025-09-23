package com.example.Mai.sProject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
public class JWTResponse {
    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
