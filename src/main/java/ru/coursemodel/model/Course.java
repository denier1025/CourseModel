package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey on 30.10.2017.
 */
@Entity
@Table(name = "course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int number;
    private float price;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Professor> professors = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pass_course_id")
    private PassCourse passCourse;

    public void setStudents(Student student) {
        this.students.add(student);
    }

    public void setProfessors(Professor professor) {
        this.professors.add(professor);
    }
}
