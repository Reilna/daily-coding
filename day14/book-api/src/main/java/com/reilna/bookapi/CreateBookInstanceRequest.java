package com.reilna.bookapi;

import jakarta.validation.constraints.NotBlank;

public record CreateBookInstanceRequest(
        @NotBlank(message = "Инвентарный номер не должен быть пустым")
        String inventoryNumber
) {
}
