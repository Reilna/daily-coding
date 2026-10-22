package com.reilna.bookapi;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookApiProperties bookApiProperties;

    private final BookRepository bookRepository;

    private final Counter booksCreatedCounter;

    public BookService(
            BookRepository bookRepository,
            BookApiProperties bookApiProperties,
            MeterRegistry meterRegistry) {
        this.bookRepository = bookRepository;
        this.bookApiProperties = bookApiProperties;
        this.booksCreatedCounter = meterRegistry.counter("books.created");
    }

    public List<Book> findAll(String author) {
        if("Все".equalsIgnoreCase(author)) {
            return bookRepository.findAll();
        }

        return bookRepository.findByAuthorIgnoreCase(author);
    }


    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND , "Книга не найдена"));
    }

    public Book create(String title, String author) {
        validateAuthor(author);
        Book book = new Book(null, title, author);
        Book savedBook = bookRepository.save(book);
        this.booksCreatedCounter.increment();
        return savedBook;
    }

    public void deleteById(Long id) {
        Book book = findById(id);

        bookRepository.delete(book);
    }

    private void validateAuthor(String author) {
        if (author.length() > bookApiProperties.getMaxAuthorLength()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Имя автора слишком длинное"
            );
        }
    }

    public Book update(Long id, String title, String author) {
        validateAuthor(author);
        Book existingBook = findById(id);

        existingBook.setTitle(title);
        existingBook.setAuthor(author);

        return bookRepository.save(existingBook);
    }

}
