package com.reilna.bookapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;


@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public List<Book> getBooks(@RequestParam(defaultValue = "Все") String author)  {
        return bookService.findAll(author);
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.create(request.title(), request.author());
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookRequest request) {

        return bookService.update(
                id,
                request.title(),
                request.author()
        );
    }
}
