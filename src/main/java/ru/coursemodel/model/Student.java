package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    @Column(name = "students_record_book_number")
    private Integer studentsRecordBookNumber;
    @Column(name = "average_performance")
    private Float averagePerformance;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PassCourse> passCourses = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void setPassCourses(Set<PassCourse> passCourses) {
        passCourses.forEach(passCourse -> this.passCourses.add(passCourse));
    }
}
