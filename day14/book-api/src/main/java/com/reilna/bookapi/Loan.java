package com.reilna.bookapi;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "api_loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_instance_id", nullable = false)
    private BookInstance bookInstance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "loaned_at", nullable = false)
    private OffsetDateTime loanedAt;

    @Column(name = "returned_at")
    private OffsetDateTime returnedAt;

    protected Loan(){
    }

    public Loan(
            Long id,
            BookInstance bookInstance,
            AppUser user,
            OffsetDateTime loanedAt,
            OffsetDateTime returnedAt) {
        this.id = id;
        this.bookInstance = bookInstance;
        this.user = user;
        this.loanedAt = loanedAt;
        this.returnedAt = returnedAt;
    }

    public Long getId() {
        return id;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public AppUser getUser() {
        return user;
    }

    public OffsetDateTime getLoanedAt() {
        return loanedAt;
    }

    public OffsetDateTime getReturnedAt() {
        return returnedAt;
    }

    public void returnBook(OffsetDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

}
