package com.reilna.bookapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Books",
        description = "Операции с книгами"
)
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(
            summary = "Получить список книг",
            description = "Возвращает список книг с фильтрацией по автору и названию"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список книг успешно получен"
    )
    @GetMapping("/books")
    public Page<Book> getBooks(
            @RequestParam(defaultValue = "Все") String author,
            @RequestParam(defaultValue = "") String title,
            @PageableDefault(sort = "id") Pageable pageable)  {
        return bookService.findAll(author, title, pageable);
    }

    @Operation(
            summary = "Получить книгу по ID",
            description = "Возвращает одну книгу по идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книга найдена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(
            summary = "Создать книгу",
            description = "Создает новую книгу после проверки входных данных"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Книга создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book createBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.create(request.title(), request.author());
    }

    @Operation(
            summary = "Удалить книгу",
            description = "Удаляет книгу по идентификатору"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Книга успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @Operation(
            summary = "Обновить книгу",
            description = "Изменяет данные существующей книги"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книга успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
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
