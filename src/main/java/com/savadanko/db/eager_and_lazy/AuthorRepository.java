package com.savadanko.JavaRepo.db.eager_and_lazy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
