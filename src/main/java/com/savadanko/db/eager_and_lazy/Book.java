package com.savadanko.JavaRepo.db.eager_and_lazy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;
}

