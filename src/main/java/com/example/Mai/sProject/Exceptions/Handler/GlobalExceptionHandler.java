package com.example.Mai.sProject.Exceptions.Handler;

import com.example.Mai.sProject.Exceptions.CourseNotFoundException;
import com.example.Mai.sProject.Exceptions.DuplicateEnrollmentException;
import com.example.Mai.sProject.Exceptions.InstructorNotFoundException;
import com.example.Mai.sProject.Exceptions.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionCommence> handleStudentNotFound(StudentNotFoundException ex) {
        ExceptionCommence error = new ExceptionCommence();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ExceptionCommence> handleCourseNotFound(CourseNotFoundException ex) {
        ExceptionCommence error = new ExceptionCommence();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DuplicateEnrollmentException.class)
    public ResponseEntity<ExceptionCommence> handleDuplicateEnrollmentException(DuplicateEnrollmentException exc) {
        ExceptionCommence error = new ExceptionCommence();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // Instructor not found
    @ExceptionHandler(InstructorNotFoundException.class)
    public ResponseEntity<ExceptionCommence> handleInstructorNotFound(InstructorNotFoundException ex) {
        ExceptionCommence error = new ExceptionCommence();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("Username is already taken!")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        if (ex.getMessage().contains("Email is already registered!")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
