package com.example.Mai.sProject.Repositories;

import com.example.Mai.sProject.Entites.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {
}
