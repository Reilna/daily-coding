package com.reilna.bookapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookInstanceService {

    private final BookRepository bookRepository;
    private final BookInstanceRepository bookInstanceRepository;

    public BookInstanceService(BookRepository bookRepository, BookInstanceRepository bookInstanceRepository) {
        this.bookRepository = bookRepository;
        this.bookInstanceRepository = bookInstanceRepository;
    }

    @Transactional
    public BookInstance create(Long bookId, String inventoryNumber) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Книга не найдена"
                ));

        BookInstance instance = new BookInstance(null, book, inventoryNumber.trim());

        return bookInstanceRepository.save(instance);
    }

    @Transactional
    public BookInstanceResponse createResponse(Long bookId, String inventoryNumber) {
        BookInstance instance = create(bookId, inventoryNumber);

        return new BookInstanceResponse(
                instance.getId(),
                instance.getBook().getId(),
                instance.getInventoryNumber()
        );
    }

}
