package com.example.Mai.sProject.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;

    private Long instructorId;
    private Set<Long> studentIds;
}
