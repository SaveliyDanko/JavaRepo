package com.savadanko.db.service;

import com.savadanko.db.domain.Student;
import com.savadanko.db.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public void addPerson(Student person){
        personRepository.save(person);
    }
}
