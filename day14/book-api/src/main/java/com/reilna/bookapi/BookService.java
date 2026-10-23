package com.reilna.bookapi;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<Book> findAll(String author, String title, Pageable pageable) {
        boolean authorIsEmpty =
                author == null
                        || author.isBlank()
                        || "Все".equalsIgnoreCase(author);

        boolean titleIsEmpty =
                title == null || title.isBlank();

        if (authorIsEmpty && titleIsEmpty) {
            return bookRepository.findAll(pageable);
        }

        if (authorIsEmpty) {
            return bookRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        }

        if (titleIsEmpty) {
            return bookRepository.findByAuthorContainingIgnoreCase(author.trim(), pageable);
        }

        return bookRepository.findByAuthorContainingIgnoreCaseAndTitleContainingIgnoreCase(
                author.trim(),
                title.trim(),
                pageable
        );
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
