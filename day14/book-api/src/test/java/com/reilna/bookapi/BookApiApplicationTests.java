package com.reilna.bookapi;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class BookApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    void shouldFindBooksByAuthorContainingIgnoringCase(){
        bookRepository.save(new Book(null, "Java", "Иван"));

        Page<Book> foundBooks = bookRepository.findByAuthorContainingIgnoreCase(
                "ван", PageRequest.of(0, 10));

        assertThat(foundBooks.getContent()).hasSize(1);
        assertThat(foundBooks.getContent().getFirst().getTitle()).isEqualTo("Java");
    }

    @Test
    @Transactional
    void shouldFindBookById(){
        Book saved = bookRepository.save(new Book(null, "Java", "Иван"));
        Optional<Book> found = bookRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java");
    }

    @Test
    @Transactional
    void shouldDeleteBook(){
        Book saved = bookRepository.save(new Book(null, "Java", "Иван"));
        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.findById(saved.getId())).isEmpty();
    }

    @Autowired
    private BookApiProperties bookApiProperties;

    @Test
    void shouldLoadBookApiProperties() {
        assertThat(bookApiProperties.getMaxAuthorLength())
                .isEqualTo(100);
    }

}
