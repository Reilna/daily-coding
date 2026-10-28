package com.reilna.bookapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "Book instances",
        description = "Физические экземпляры книг"
)
public class BookInstanceController {

    private final BookInstanceService bookInstanceService;

    public BookInstanceController(BookInstanceService bookInstanceService) {
        this.bookInstanceService = bookInstanceService;
    }

    @Operation(
            summary = "Добавить экземпляр книги",
            description = "Создаёт физический экземпляр существующей книги"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Экземпляр создан"),
            @ApiResponse(responseCode = "400", description = "Некорректный инвентарный номер"),
            @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
            @ApiResponse(responseCode = "403", description = "Требуется роль ADMIN"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
    @PostMapping("/books/{bookId}/instances")
    @ResponseStatus(HttpStatus.CREATED)
    public BookInstanceResponse create(
            @PathVariable Long bookId,
            @Valid @RequestBody CreateBookInstanceRequest request) {

        return bookInstanceService.createResponse(
                bookId,
                request.inventoryNumber()
        );
    }
}
