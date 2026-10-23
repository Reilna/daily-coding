package com.reilna.bookapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    Page<Book> findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(
            String author,
            String title,
            Pageable pageable
    );
    Page<Book> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );
}
