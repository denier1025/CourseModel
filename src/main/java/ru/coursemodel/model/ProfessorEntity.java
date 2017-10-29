package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "professor")
@Data
public class ProfessorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Float payment;

    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    @OneToMany(mappedBy = "professorEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CourseEntity> courses = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    public void setCourses(CourseEntity course) {
        this.courses.add(course);
    }
}
