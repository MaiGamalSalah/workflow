
package com.example.Mai.sProject.user_config;

import com.example.Mai.sProject.Entites.Role;
import com.example.Mai.sProject.Entites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    private User testUser;
    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "testuser@example.com", "password", Role.USER);
        testUser.setId(1L);
        userInfo = new UserInfo(testUser);
    }

    @Test
    void getAuthorities_ShouldReturnUserRole() {
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }
}
