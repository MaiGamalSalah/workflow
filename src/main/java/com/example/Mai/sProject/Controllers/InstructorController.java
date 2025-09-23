package com.example.Mai.sProject.Controllers;

import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Entites.Instructor;

import com.example.Mai.sProject.Services.InstructorServiceImpl;
import lombok.Data;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Set;

@RestController
@Data
@RequestMapping("/instructors")
public class InstructorController {
    private InstructorServiceImpl instructorService;

    public InstructorController(InstructorServiceImpl instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    public Instructor createInstructor(@RequestBody Instructor instructor) {
        return instructorService.save(instructor);
    }

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}/courses")
    public List<Courses> getCoursesForInstructor(@PathVariable Long id) {
        return instructorService.getCoursesForInstructor(id);
    }
}
