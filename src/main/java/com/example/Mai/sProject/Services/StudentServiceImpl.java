package com.example.Mai.sProject.Services;

import com.example.Mai.sProject.Entites.Courses;
import com.example.Mai.sProject.Entites.Students;
import com.example.Mai.sProject.Exceptions.CourseNotFoundException;
import com.example.Mai.sProject.Exceptions.DuplicateEnrollmentException;
import com.example.Mai.sProject.Exceptions.StudentNotFoundException;
import com.example.Mai.sProject.Repositories.CourseRepository;
import com.example.Mai.sProject.Repositories.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class StudentServiceImpl implements StudentService{


    StudentRepository studentRepository;
    private CourseRepository courseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        //this.entityManager=entityManager;
    }
    @Override
    //add and update
    public Students save(Students students) {
       return studentRepository.save(students);
    }



    @Override
    public void deleteStudents(Long id) {
        studentRepository.deleteById(id);

    }

    @Override
    public List<Students> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Students getStudentById(Long id) {
        Optional<Students>st=studentRepository.findById(id);
        if(st.isPresent())return st.get();
        throw new StudentNotFoundException("Student not found with id: " + id);
    }


    @Override
    public Students enrollStudentInCourse(Long studentId, Long courseId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        if (student.getCourses().contains(course)) {
            throw new DuplicateEnrollmentException("Student already enrolled in this course");
        }

        student.getCourses().add(course);
        return studentRepository.save(student);
    }
    @Override
    public List<Students> findStudentsByCourseTitle(String title) {
        return studentRepository.findStudentsByCourseTitle(title);
    }






    @Override
    public List<Students> findStudentsByLastNameStart(String prefix) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Students> query = cb.createQuery(Students.class);
        Root<Students> student = query.from(Students.class);
        query.select(student)
                .where(cb.like(cb.lower(student.get("lastName")), prefix.toLowerCase() + "%"));
        return entityManager.createQuery(query).getResultList();
    }
    @Override
    //@Transactional(rollbackFor = {CourseNotFoundException.class, StudentNotFoundException.class, DuplicateEnrollmentException.class})

    @Transactional
    public Students enrollStudentInMultipleCourses(Long studentId, List<Long> courseIds) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        for (Long courseId : courseIds) {
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

            if (student.getCourses().contains(course)) {
                throw new DuplicateEnrollmentException("Student already enrolled in course with id: " + courseId);
            }

            student.getCourses().add(course);
        }

        return studentRepository.save(student);
    }

}
