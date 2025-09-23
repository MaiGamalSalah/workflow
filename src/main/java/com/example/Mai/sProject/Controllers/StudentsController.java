package com.example.Mai.sProject.Controllers;

import com.example.Mai.sProject.Entites.Students;
import com.example.Mai.sProject.Services.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentsController {

    private StudentService studentService;

    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    List<Students>getAll(){
        return studentService.getAllStudents();
    }
    @GetMapping("/students/{id}")
    Students getAllById(@PathVariable long id){
        return studentService.getStudentById(id);
    }


    @PostMapping("/students")
    void createStudent(@RequestBody Students st){
        studentService.save(st);
    }
    @PutMapping("/students/{id}")
    void updateStudent(@PathVariable long id, @RequestBody Students st){
        st.setId(id);
        studentService.save(st);
    }
    @DeleteMapping("/students/{id}")
    void deleteStudent(@PathVariable long id ){
        studentService.deleteStudents(id);
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    Students enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentService.enrollStudentInCourse(studentId, courseId);
    }

    @GetMapping("/students/by-course")
    public List<Students> getStudentsByCourseTitle(@RequestParam String title) {
        return studentService.findStudentsByCourseTitle(title);
    }

    @GetMapping("/search")
    public List<Students> searchStudentsByLastNameStart(@RequestParam("lastNameStart") String lastNameStart) {
        return studentService.findStudentsByLastNameStart(lastNameStart);
    }


    @PutMapping("/{studentId}/enroll-multiple")
    public Students enrollStudentInMultipleCourses(
            @PathVariable Long studentId,
            @RequestBody List<Long> courseIds) {
        return studentService.enrollStudentInMultipleCourses(studentId, courseIds);
    }

}
