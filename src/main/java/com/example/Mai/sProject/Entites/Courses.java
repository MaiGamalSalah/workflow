package com.example.Mai.sProject.Entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Courses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="title",nullable = false)
    private String title;
    private String description;


    @ManyToMany(mappedBy = "courses")
    @JsonIgnoreProperties("courses")
    private Set<Students> students = new HashSet<>();


    @ManyToOne

    @JoinColumn(name = "instructor_id")
    @JsonBackReference
    private Instructor instructor;






}
