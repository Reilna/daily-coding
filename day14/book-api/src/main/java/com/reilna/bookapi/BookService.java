package com.reilna.bookapi;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        Book book = new Book(null, title, author);
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        Book book = findById(id);

        bookRepository.delete(book);
    }

    public Book update(Long id, String title, String author) {
        Book existingBook = findById(id);

        existingBook.setTitle(title);
        existingBook.setAuthor(author);

        return bookRepository.save(existingBook);
    }

}
