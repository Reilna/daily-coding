package com.reilna.bookapi;
import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(
        @NotBlank(message = "Название не должно быть пустым") String title,
        @NotBlank(message = "Автор не должен быть пустым") String author
) {

}
