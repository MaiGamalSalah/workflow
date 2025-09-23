package com.example.Mai.sProject.Controllers;

import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CoursesController {


    private  CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("/courses")
    List<Courses> getAll() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    Courses getById(@PathVariable long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/courses")
    void createCourse(@RequestBody Courses course) {
        courseService.save(course);
    }

    @PutMapping("/courses/{id}")
    void updateCourse(@PathVariable long id,@RequestBody Courses course) {
        course.setId(id);
        courseService.save(course);
    }

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
    }

    @PutMapping("/courses/{courseId}/assign-instructor/{instructorId}")
    public Courses assignInstructorToCourse(
            @PathVariable Long courseId,
            @PathVariable Long instructorId) {
        return courseService.assignInstructorToCourse(courseId, instructorId);
    }

    @GetMapping("/courses/search")
    public List<Courses> searchCourses(@RequestParam String keyword) {
        return courseService.searchCoursesByTitle(keyword);
    }
}
