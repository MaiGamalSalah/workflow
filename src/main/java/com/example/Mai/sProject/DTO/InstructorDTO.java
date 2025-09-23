package com.example.Mai.sProject.DTO;

import lombok.Data;

import java.util.List;

@Data
public class InstructorDTO {
    private Long id;
    private String name;
    private String email;

    private List<Long> courseIds;
}

