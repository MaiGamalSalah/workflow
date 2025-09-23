package com.example.Mai.sProject.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class StudentDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<Long> courseIds;
}
