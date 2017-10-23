package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int number;
    private float price;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<StudentEntity> students;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professorEntity;

    @JsonManagedReference
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProfessorEntity> professors;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "pass_course_id")
    private PassCourseEntity passCourseEntity;

    public CourseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }

    public ProfessorEntity getProfessorEntity() {
        return professorEntity;
    }

    public void setProfessorEntity(ProfessorEntity professorEntity) {
        this.professorEntity = professorEntity;
    }

    public Set<ProfessorEntity> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<ProfessorEntity> professors) {
        this.professors = professors;
    }

    public PassCourseEntity getPassCourseEntity() {
        return passCourseEntity;
    }

    public void setPassCourseEntity(PassCourseEntity passCourseEntity) {
        this.passCourseEntity = passCourseEntity;
    }

}
