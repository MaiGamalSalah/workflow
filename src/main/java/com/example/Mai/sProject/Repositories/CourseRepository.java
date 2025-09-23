package com.example.Mai.sProject.Repositories;

import com.example.Mai.sProject.Entites.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Long> {
    @Query("FROM Courses c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY c.title ASC")
    List<Courses> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);
}
