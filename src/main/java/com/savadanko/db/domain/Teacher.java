package com.savadanko.db.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @ManyToMany(mappedBy = "teachers")
    private List<Student> students;

    public Teacher(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.students = new ArrayList<>();
    }

    public void addPerson(Student person) {
        if (!students.contains(person)) {
            students.add(person);
            person.addTeacher(this);
        }
    }
}
