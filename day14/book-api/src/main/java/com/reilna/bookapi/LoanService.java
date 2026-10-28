package com.reilna.bookapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

@Service
public class LoanService {

    private final BookInstanceRepository bookInstanceRepository;
    private final AppUserRepository appUserRepository;
    private final LoanRepository loanRepository;

    public LoanService(
            BookInstanceRepository bookInstanceRepository,
            AppUserRepository appUserRepository,
            LoanRepository loanRepository) {
        this.bookInstanceRepository = bookInstanceRepository;
        this.appUserRepository = appUserRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public Loan createLoan(Long bookInstanceId, String username) {
        BookInstance bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Экземпляр книги не найден"
                ));

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Пользователь не найден"
                ));

        if (loanRepository
                .findByBookInstanceIdAndReturnedAtIsNull(bookInstanceId)
                .isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Экземпляр уже выдан"
            );
        }

        Loan loan = new Loan(
                null,
                bookInstance,
                user,
                OffsetDateTime.now(),
                null
        );

        return loanRepository.save(loan);
    }

    @Transactional
    public LoanResponse createLoanResponse(Long bookInstanceId, String username) {
        Loan loan = createLoan(bookInstanceId, username);

        return new LoanResponse(
                loan.getId(),
                loan.getBookInstance().getId(),
                loan.getUser().getUsername(),
                loan.getLoanedAt(),
                loan.getReturnedAt()
        );
    }

    @Transactional
    public LoanResponse returnLoanResponse(Long loanId, String username) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Выдача не найдена"
                        ));

        if (!loan.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Нельзя вернуть чужую выдачу"
            );
        }

        if (loan.getReturnedAt() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Книга уже возвращена"
            );
        }

        loan.returnBook(OffsetDateTime.now());
        Loan savedLoan = loanRepository.save(loan);

        return new LoanResponse(
                savedLoan.getId(),
                savedLoan.getBookInstance().getId(),
                savedLoan.getUser().getUsername(),
                savedLoan.getLoanedAt(),
                savedLoan.getReturnedAt()
        );
    }
}
