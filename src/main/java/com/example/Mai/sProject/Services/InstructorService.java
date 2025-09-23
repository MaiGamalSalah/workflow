package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Entites.Instructor;

import java.util.List;
import java.util.Set;

public interface InstructorService {
    Instructor save(Instructor instructor);
    List<Instructor> getAllInstructors();
    public Instructor getInstructorById(Long id);
    public List<Courses> getCoursesForInstructor(Long instructorId);
}
