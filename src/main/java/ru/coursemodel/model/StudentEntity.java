package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentEntity {
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

    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PassCourseEntity> passCourses = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;
}
