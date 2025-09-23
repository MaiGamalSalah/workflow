package com.example.Mai.sProject.Entites;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;
    public User(String username, String email, String encode, Role role) {
        this.username = username;
        this.email = email;
        this.password = encode;
        this.role = role;
    }
}
