package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.Entites.Students;

import java.util.List;

public interface StudentService {
    Students save(Students students);
    //Students updateStudent(Long id,Students students);
    void deleteStudents(Long id);
    List<Students> getAllStudents();
    Students getStudentById(Long id);

    public Students enrollStudentInCourse(Long studentId, Long courseId);
    List<Students> findStudentsByCourseTitle(String title);
    public List<Students> findStudentsByLastNameStart(String prefix);
    Students enrollStudentInMultipleCourses(Long studentId, List<Long> courseIds);

}
