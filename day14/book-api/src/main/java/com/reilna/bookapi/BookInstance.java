package com.reilna.bookapi;

import jakarta.persistence.*;

@Entity
@Table(name = "api_book_instances")
public class BookInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "inventory_number", nullable = false, unique = true, length = 100)
    private String inventoryNumber;

    protected BookInstance() {
    }

    public BookInstance(Long id, Book book, String inventoryNumber) {
        this.id = id;
        this.book = book;
        this.inventoryNumber = inventoryNumber;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

}
