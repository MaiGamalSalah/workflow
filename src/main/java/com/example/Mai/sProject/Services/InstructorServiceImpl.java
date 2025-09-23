package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Entites.Instructor;
import com.example.Mai.sProject.Exceptions.InstructorNotFoundException;
import com.example.Mai.sProject.Repositories.CourseRepository;
import com.example.Mai.sProject.Repositories.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class InstructorServiceImpl implements InstructorService{
    private  InstructorRepository instructorRepository;
    private  CourseRepository courseRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;

    }

    @Override
    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + id));
    }

    @Override
    public List<Courses> getCoursesForInstructor(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));
        return new ArrayList<>(instructor.getCourses());
    }
}
