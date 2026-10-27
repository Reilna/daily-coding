package com.reilna.bookapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByBookInstanceIdAndReturnedAtIsNull(Long bookInstanceId);

    List<Loan> findByUserIdAndReturnedAtIsNull(Long userId);
}
