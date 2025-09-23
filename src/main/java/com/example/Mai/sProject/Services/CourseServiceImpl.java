package com.example.Mai.sProject.Services;
import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Entites.Instructor;
import com.example.Mai.sProject.Exceptions.CourseNotFoundException;
import com.example.Mai.sProject.Exceptions.InstructorNotFoundException;
import com.example.Mai.sProject.Repositories.CourseRepository;
import com.example.Mai.sProject.Repositories.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{
    private final CourseRepository courseRepository;
    private  InstructorRepository instructorRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             InstructorRepository instructorRepository) {

        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Courses save(Courses course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);

    }

    @Override
    public List<Courses> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Courses getCourseById(Long id) {
        Optional<Courses> course = courseRepository.findById(id);
        if (course.isPresent()) return course.get();
        throw new CourseNotFoundException("Course not found with id: " + id);
    }

    // Assign instructor to course
    public Courses assignInstructorToCourse(Long courseId, Long instructorId) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFoundException("Instructor not found with id: " + instructorId));

        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    @Override
    public List<Courses> searchCoursesByTitle(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
