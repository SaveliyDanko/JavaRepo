package com.savadanko.db.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    private Dog dog;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Teacher> teachers;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.teachers = new ArrayList<>();
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            teacher.addPerson(this);
        }
    }
}
