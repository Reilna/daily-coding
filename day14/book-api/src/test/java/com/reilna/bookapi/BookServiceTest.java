package com.reilna.bookapi;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookApiProperties bookApiProperties;

    @Mock
    private BookInstanceRepository bookInstanceRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private LoanRepository loanRepository;

    @Test
    void shouldFindBookById() {
        Book book = new Book(1L, "Java", "Иван");

        when(bookRepository.findById(1L))
                .thenReturn(java.util.Optional.of(book));

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

        Book result = bookService.findById(1L);

        assertEquals("Java", result.getTitle());
        assertEquals("Иван", result.getAuthor());
    }

    @Test
    void shouldThrow404WhenBookNotFound() {
        when(bookRepository.findById(999L))
                .thenReturn(java.util.Optional.empty());

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

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
        PageRequest pageable = PageRequest.of(0, 10);

        when(bookRepository.findByAuthorContainingIgnoreCase("Иван", pageable))
                .thenReturn(new PageImpl<>(List.of(firstBook), pageable, 1));

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

        Page<Book> foundBooks = bookService.findAll("Иван", "", pageable);

        assertThat(foundBooks.getContent()).hasSize(1);
        assertThat(foundBooks.getContent().getFirst().getTitle()).isEqualTo("Java");
    }

    @Test
    void shouldRejectAuthorThatIsTooLong() {
        when(bookApiProperties.getMaxAuthorLength())
                .thenReturn(3);

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> bookService.create("Java", "Иван")
        );

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void shouldRejectLongAuthorOnUpdate() {
        when(bookApiProperties.getMaxAuthorLength())
                .thenReturn(3);

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> bookService.update(1L, "Java", "Иван"));

        assertEquals(400, exception.getStatusCode().value());
    }

    @Test
    void shouldAcceptAuthorAtMaximumLength() {
        when(bookApiProperties.getMaxAuthorLength())
                .thenReturn(4);

        BookService bookService = new BookService(bookRepository, bookApiProperties, new SimpleMeterRegistry());

        assertDoesNotThrow(() ->
                bookService.create("Java", "Иван")
        );
    }

    @Test
    void shouldCreateLoanForAvailableBookInstance() {
        Book book = new Book(1L, "Effective Java", "Joshua Bloch");
        BookInstance instance = new BookInstance(10L, book, "INV-001");
        AppUser user = new AppUser(20L, "reader", "hash", "USER");

        when(bookInstanceRepository.findById(10L))
                .thenReturn(Optional.of(instance));
        when(appUserRepository.findByUsername("reader"))
                .thenReturn(Optional.of(user));
        when(loanRepository.findByBookInstanceIdAndReturnedAtIsNull(10L))
                .thenReturn(Optional.empty());
        when(loanRepository.save(org.mockito.ArgumentMatchers.any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument    (0));

        LoanService loanService = new LoanService(
                bookInstanceRepository,
                appUserRepository,
                loanRepository
        );

        Loan result = loanService.createLoan(10L, "reader");

        assertThat(result.getBookInstance()).isEqualTo(instance);
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getLoanedAt()).isNotNull();
        assertThat(result.getReturnedAt()).isNull();

        verify(loanRepository).save(org.mockito.ArgumentMatchers.any(Loan.class));
    }

    @Test
    void shouldRejectLoanWhenBookInstanceIsAlreadyActive() {
        Book book = new Book(1L, "Effective Java", "Joshua Bloch");
        BookInstance instance = new BookInstance(10L, book, "INV-001");
        AppUser user = new AppUser(20L, "reader", "hash", "USER");

        Loan activeLoan = new Loan(
                30L,
                instance,
                user,
                OffsetDateTime.now(),
                null
        );

        when(bookInstanceRepository.findById(10L))
                .thenReturn(Optional.of(instance));
        when(appUserRepository.findByUsername("reader"))
                .thenReturn(Optional.of(user));
        when(loanRepository.findByBookInstanceIdAndReturnedAtIsNull(10L))
                .thenReturn(Optional.of(activeLoan));

        LoanService loanService = new LoanService(
                bookInstanceRepository,
                appUserRepository,
                loanRepository
        );

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> loanService.createLoan(10L, "reader")
        );

        assertEquals(409, exception.getStatusCode().value());
        verify(loanRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Loan.class));
    }

    @Test
    void shouldReturnOwnActiveLoan() {
        Book book = new Book(1L, "Effective Java", "Joshua Bloch");
        BookInstance instance = new BookInstance(10L, book, "INV-001");
        AppUser user = new AppUser(20L, "reader", "hash", "USER");

        Loan loan = new Loan(
                30L,
                instance,
                user,
                OffsetDateTime.now().minusDays(1),
                null
        );

        when(loanRepository.findById(30L))
                .thenReturn(Optional.of(loan));
        when(loanRepository.save(loan))
                .thenReturn(loan);

        LoanService loanService = new LoanService(
                bookInstanceRepository,
                appUserRepository,
                loanRepository
        );

        LoanResponse result = loanService.returnLoanResponse(30L, "reader");

        assertThat(result.id()).isEqualTo(30L);
        assertThat(result.username()).isEqualTo("reader");
        assertThat(result.returnedAt()).isNotNull();

        verify(loanRepository).save(loan);
    }

    @Test
    void shouldRejectReturningAnotherUsersLoan() {
        Book book = new Book(1L, "Effective Java", "Joshua Bloch");
        BookInstance instance = new BookInstance(10L, book, "INV-001");
        AppUser owner = new AppUser(20L, "reader", "hash", "USER");

        Loan loan = new Loan(
                30L,
                instance,
                owner,
                OffsetDateTime.now(),
                null
        );

        when(loanRepository.findById(30L))
                .thenReturn(Optional.of(loan));

        LoanService loanService = new LoanService(
                bookInstanceRepository,
                appUserRepository,
                loanRepository
        );

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> loanService.returnLoanResponse(30L, "other-user")
        );

        assertEquals(403, exception.getStatusCode().value());
        verify(loanRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Loan.class));
    }

    @Test
    void shouldRejectReturningAlreadyReturnedLoan() {
        Book book = new Book(1L, "Effective Java", "Joshua Bloch");
        BookInstance instance = new BookInstance(10L, book, "INV-001");
        AppUser user = new AppUser(20L, "reader", "hash", "USER");

        Loan loan = new Loan(
                30L,
                instance,
                user,
                OffsetDateTime.now().minusDays(2),
                OffsetDateTime.now().minusDays(1)
        );

        when(loanRepository.findById(30L))
                .thenReturn(Optional.of(loan));

        LoanService loanService = new LoanService(
                bookInstanceRepository,
                appUserRepository,
                loanRepository
        );

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> loanService.returnLoanResponse(30L, "reader")
        );

        assertEquals(409, exception.getStatusCode().value());
        verify(loanRepository, org.mockito.Mockito.never())
                .save(org.mockito.ArgumentMatchers.any(Loan.class));
    }
}
