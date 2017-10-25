package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "course")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int number;
    private float price;

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StudentEntity> students = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professorEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProfessorEntity> professors = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pass_course_id")
    private PassCourseEntity passCourseEntity;
}
