package com.reilna.bookapi;

public record BookInstanceResponse(
        Long id,
        Long bookId,
        String inventoryNumber
) {

}
