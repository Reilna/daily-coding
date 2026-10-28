package com.reilna.bookapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
        name = "Loans",
        description = "Выдача экземпляров книг"
)
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(
            summary = "Выдать экземпляр книги",
            description = "Создаёт активную выдачу для текущего авторизованного пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Экземпляр выдан"),
            @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
            @ApiResponse(responseCode = "404", description = "Экземпляр или пользователь не найден"),
            @ApiResponse(responseCode = "409", description = "Экземпляр уже выдан")
    })
    @PostMapping("/book-instances/{id}/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponse createLoan(
            @PathVariable Long id,
            Authentication authentication) {

        return loanService.createLoanResponse(
                id,
                authentication.getName()
        );
    }

    @Operation(
            summary = "Вернуть экземпляр книги",
            description = "Закрывает активную выдачу текущего пользователя"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книга возвращена"),
            @ApiResponse(responseCode = "401", description = "Требуется авторизация"),
            @ApiResponse(responseCode = "403", description = "Выдача принадлежит другому пользователю"),
            @ApiResponse(responseCode = "404", description = "Выдача не найдена"),
            @ApiResponse(responseCode = "409", description = "Книга уже возвращена")
    })
    @PostMapping("/loans/{loanId}/return")
    public LoanResponse returnLoan(
            @PathVariable Long loanId,
            Authentication authentication) {

        return loanService.returnLoanResponse(loanId, authentication.getName());
    }
}
