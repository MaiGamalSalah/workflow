package com.example.Mai.sProject.Services;


import com.example.Mai.sProject.Entites.Courses;

import java.util.List;

public interface CourseService {
    Courses save(Courses course);
    void deleteCourse(Long id);
    List<Courses> getAllCourses();
    Courses getCourseById(Long id);
    public Courses assignInstructorToCourse(Long courseId, Long instructorId);
    List<Courses> searchCoursesByTitle(String keyword);
}
