package com.reilna.bookapi;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Test
    void shouldFindBookById() {
        Book book = new Book(1L, "Java", "Иван");

        when(bookRepository.findById(1L))
                .thenReturn(java.util.Optional.of(book));

        BookService bookService = new BookService(bookRepository);

        Book result = bookService.findById(1L);

        assertEquals("Java", result.getTitle());
        assertEquals("Иван", result.getAuthor());
    }

    @Test
    void shouldThrow404WhenBookNotFound() {
        when(bookRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        BookService bookService = new BookService(bookRepository);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> bookService.findById(999L)
        );

        assertEquals(404, exception.getStatusCode().value());
    }

    @Test
    void shouldFilterBookByAuthor() {
        Book firstBook = new Book(1L, "Java", "Иван");
        Book secondBook = new Book(2L, "Spring", "Анна");

        when(bookRepository.findByAuthorIgnoreCase("Иван"))
                .thenReturn(List.of(firstBook));

        BookService bookService = new BookService(bookRepository);

        List<Book> result = bookService.findAll("Иван");

        assertEquals(1, result.size());
        assertEquals("Иван", result.get(0).getAuthor());
    }
}
