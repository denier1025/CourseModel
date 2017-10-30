package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Alexey on 30.10.2017.
 */
@Entity
@Table(name = "pass_course")
@Data
public class PassCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mark;

    @OneToOne(mappedBy = "passCourse", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Course course;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
