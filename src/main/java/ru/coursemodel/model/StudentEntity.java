package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ru.coursemodel.model.CourseEntity;
import ru.coursemodel.model.PassCourseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "student")
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

    @JsonManagedReference
    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CourseEntity> courses;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    public StudentEntity() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStudentsRecordBookNumber() {
        return studentsRecordBookNumber;
    }

    public void setStudentsRecordBookNumber(Integer studentsRecordBookNumber) {
        this.studentsRecordBookNumber = studentsRecordBookNumber;
    }

    public Float getAveragePerformance() {
        return averagePerformance;
    }

    public void setAveragePerformance(Float averagePerformance) {
        this.averagePerformance = averagePerformance;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }
}
