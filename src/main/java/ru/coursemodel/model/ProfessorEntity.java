package ru.coursemodel.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexey on 22.10.2017.
 */
@Entity
@Table(name = "professor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProfessorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private Float payment;

    @JsonManagedReference
    @OneToMany(mappedBy = "professorEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CourseEntity> courses = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;
}
