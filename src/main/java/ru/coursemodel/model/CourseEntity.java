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
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "course")
@Data
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int number;
    private float price;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StudentEntity> students = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professorEntity;

    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProfessorEntity> professors = new HashSet<>();

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pass_course_id")
    private PassCourseEntity passCourseEntity;

    public void setStudents(StudentEntity student) {
        this.students.add(student);
    }

    public void setProfessors(ProfessorEntity professor) {
        this.professors.add(professor);
    }
}
