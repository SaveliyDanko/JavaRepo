package com.savadanko.JavaRepo.db.eager_and_lazy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    @Transactional
    public void createData() {
        Author author = new Author("Лев Толстой");
        author.addBook(new Book("Война и мир"));
        author.addBook(new Book("Анна Каренина"));
        repository.save(author);
    }

    @Transactional(readOnly = true)
    public void testLazy() {
        /*
        Lazy:
        Hibernate:
            select
                a1_0.id,
                a1_0.name
            from
                author a1_0
            where
                a1_0.id=?

         Eager
         Hibernate:
            select
                a1_0.id,
                a1_0.name,
                b1_0.author_id,
                b1_0.id,
                b1_0.title
            from
                author a1_0
            left join
                book b1_0
                    on a1_0.id=b1_0.author_id
            where
                a1_0.id=?
         */

        Author author = repository.findById(1L).orElseThrow();
        System.out.println("Автор: " + author.getName());
        System.out.println("-----");

        // 🔹 Здесь пока запросов к книгам нет (Lazy)
        System.out.println("Количество книг: " + author.getBooks().size());
        // ⚠️ Только при обращении к getBooks() Hibernate выполнит SQL SELECT
    }
}

