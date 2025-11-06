package com.savadanko.db.repository;

import com.savadanko.db.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Student, Long> {
}
