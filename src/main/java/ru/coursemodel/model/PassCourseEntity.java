package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "pass_course")
@Data
public class PassCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mark;

    @OneToOne(mappedBy = "passCourseEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CourseEntity courseEntity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;
}
