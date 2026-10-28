package com.reilna.bookapi;

import jakarta.transaction.Transactional;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import tools.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class BookApiApplicationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17-alpine");

    @Test
    void contextLoads() {
    }

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldFindBooksByAuthorContainingIgnoringCase(){
        bookRepository.save(new Book(null, "Java", "Иван"));

        Page<Book> foundBooks = bookRepository.findByAuthorContainingIgnoreCase(
                "ван", PageRequest.of(0, 10));

        assertThat(foundBooks.getContent()).hasSize(1);
        assertThat(foundBooks.getContent().getFirst().getTitle()).isEqualTo("Java");
    }

    @Test
    @Transactional
    void shouldFindBookById(){
        Book saved = bookRepository.save(new Book(null, "Java", "Иван"));
        Optional<Book> found = bookRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java");
    }

    @Test
    @Transactional
    void shouldDeleteBook(){
        Book saved = bookRepository.save(new Book(null, "Java", "Иван"));
        bookRepository.deleteById(saved.getId());

        assertThat(bookRepository.findById(saved.getId())).isEmpty();
    }

    @Autowired
    private BookApiProperties bookApiProperties;

    @Test
    void shouldLoadBookApiProperties() {
        assertThat(bookApiProperties.getMaxAuthorLength())
                .isEqualTo(100);
    }

    @Test
    @Transactional
    void shouldFindBooksByTitleWithPaginationAndSorting() {
        bookRepository.saveAll(List.of(
                new Book(null, "Java C", "Автор 1"),
                new Book(null, "Java A", "Автор 2"),
                new Book(null, "Java B", "Автор 3")
        ));

        Page<Book> foundBooks =
                bookRepository.findByTitleContainingIgnoreCase(
                        "java",
                        PageRequest.of(
                                0,
                                2,
                                Sort.by(Sort.Direction.ASC, "title")
                        )
                );

        assertThat(foundBooks.getTotalElements()).isEqualTo(3);
        assertThat(foundBooks.getNumberOfElements()).isEqualTo(2);
        assertThat(foundBooks.hasNext()).isTrue();

        assertThat(foundBooks.getContent().get(0).getTitle())
                .isEqualTo("Java A");
        assertThat(foundBooks.getContent().get(1).getTitle())
                .isEqualTo("Java B");
    }
    @Test
    @Transactional
    void shouldCreateAdminAndReaderUsers() {
        AppUser admin = appUserRepository.findByUsername("admin")
                .orElseThrow();

        AppUser reader = appUserRepository.findByUsername("reader")
                .orElseThrow();

        assertThat(admin.getRole()).isEqualTo("ADMIN");
        assertThat(reader.getRole()).isEqualTo("USER");

        assertThat(passwordEncoder.matches("admin123", admin.getPasswordHash()))
                .isTrue();

        assertThat(passwordEncoder.matches("reader123", reader.getPasswordHash()))
                .isTrue();
    }

    @Test
    @Transactional
    void shouldSaveBookInstanceWithBookRelation() {
        Book book = bookRepository.save(new Book(null, "Effective Java", "Joshua Bloch"));

        BookInstance instance = bookInstanceRepository.save(new BookInstance(null, book, "INV-001"));

        Optional<BookInstance> found = bookInstanceRepository.findById(instance.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getInventoryNumber()).isEqualTo("INV-001");
        assertThat(found.get().getBook().getTitle()).isEqualTo("Effective Java");
    }

    @Test
    @Transactional
    void shouldFindActiveLoanForBookInstance() {
        Book book = bookRepository.save(new Book(null, "Effective Java", "Joshua Bloch"));

        BookInstance instance = bookInstanceRepository.save(new BookInstance(null, book, "INV-002"));

        AppUser user = appUserRepository.findByUsername("reader")
                .orElseThrow();

        Loan loan = loanRepository.save(
                new Loan(
                        null,
                        instance,
                        user,
                        OffsetDateTime.now(),
                        null
                )
        );

        Optional<Loan> activeLoan = loanRepository.findByBookInstanceIdAndReturnedAtIsNull(instance.getId());

        assertThat(activeLoan).isPresent();
        assertThat(activeLoan.get().getId()).isEqualTo(loan.getId());
        assertThat(activeLoan.get().getUser().getUsername())
                .isEqualTo("reader");
    }

    @Test
    void shouldCreateLoanAndRejectSecondActiveLoan() throws Exception {
        Book book = bookRepository.saveAndFlush(new Book(null, "Effective Java", "Joshua Bloch"));

        BookInstance instance = bookInstanceRepository.saveAndFlush(new BookInstance(null, book, "INV-HTTP-001"));

        mockMvc.perform(post("/book-instances/{id}/loans", instance.getId())
                .with(httpBasic("reader", "reader123")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookInstanceId").value(instance.getId()))
                .andExpect(jsonPath("$.username").value("reader"))
                .andExpect(jsonPath("$.returnedAt").doesNotExist());

        mockMvc.perform(post("/book-instances/{id}/loans", instance.getId())
                .with(httpBasic("reader", "reader123")))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnLoanAndAllowSameInstanceToBeLoanedAgain() throws Exception {
        Book book = bookRepository.saveAndFlush(new Book(null, "Effective Java", "Joshua Bloch"));

        BookInstance instance = bookInstanceRepository.saveAndFlush(new BookInstance(null, book, "INV-RETURN-001"));

        String firstLoanResponse = mockMvc.perform(
                post("/book-instances/{id}/loans", instance.getId())
                        .with(httpBasic("reader", "reader123"))
        )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long loanId = objectMapper
                .readTree(firstLoanResponse)
                .get("id")
                .asLong();

        mockMvc.perform(
                post("/loans/{loanId}/return", loanId)
                        .with(httpBasic("reader", "reader123"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnedAt").exists());

        mockMvc.perform(
                post("/book-instances/{id}/loans", instance.getId())
                        .with(httpBasic("reader", "reader123"))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookInstanceId").value(instance.getId()))
                .andExpect(jsonPath("$.returnedAt").doesNotExist());
    }

    @Test
    void shouldAllowAdminToCreateInstanceThenReaderToLoanAndReturnIt() throws Exception {
        Book book = bookRepository.saveAndFlush(new Book(null, "Clean Code", "Robert Martin"));

        String instanceResponse = mockMvc.perform(
                post("/books/{bookId}/instances", book.getId())
                        .with(httpBasic("admin", "admin123"))
                        .contentType("application/json")
                        .content("""
                                {
                                    "inventoryNumber": "INV-ADMIN-001"
                                }
                                """)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(book.getId()))
                .andExpect(jsonPath("$.inventoryNumber")
                        .value("INV-ADMIN-001"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long instanceId = objectMapper
                .readTree(instanceResponse)
                .get("id")
                .asLong();

        String loanResponse = mockMvc.perform(
                post("/book-instances/{id}/loans", instanceId)
                        .with(httpBasic("reader", "reader123"))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookInstanceId").value(instanceId))
                .andExpect(jsonPath("$.username").value("reader"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long loanId = objectMapper
                .readTree(loanResponse)
                .get("id")
                .asLong();

        mockMvc.perform(
                post("/loans/{loanId}/return", loanId)
                        .with(httpBasic("reader", "reader123"))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnedAt").exists());
    }

}
