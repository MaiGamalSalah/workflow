package com.example.Mai.sProject.Repositories;

import com.example.Mai.sProject.Entites.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Students,Long> {
    @Query("FROM Students s JOIN FETCH s.courses c WHERE LOWER(c.title) = LOWER(:title)")
    List<Students> findStudentsByCourseTitle(@Param("title") String title);
}
