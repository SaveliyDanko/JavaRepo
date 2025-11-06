package com.savadanko.db;

import com.savadanko.db.domain.Student;
import com.savadanko.db.domain.Teacher;
import com.savadanko.db.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {
    private final PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student person1 = new Student("Sava", 20);
        Teacher teacher = new Teacher("Martin", 28);

        Student person2 = new Student("Liza", 20);


        person1.addTeacher(teacher);

        personService.addPerson(person1);
        personService.addPerson(person2);
    }
}
