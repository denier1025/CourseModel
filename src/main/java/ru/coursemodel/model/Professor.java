package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "professor")
@Data
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Float payment;

    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void setCourses(Set<Course> courses) {
        courses.forEach(course -> this.courses.add(course));
    }
}
