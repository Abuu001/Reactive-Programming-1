package com.example.reactive_programming_tut1.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class BookServiceTest {

    private BookInfoService bookInfoService = new BookInfoService();
    private ReviewService reviewService = new ReviewService();
    private  BookService bookService = new BookService(bookInfoService,reviewService);
    @Test
    void getBooks() {
        var books = bookService.getBooks();
        StepVerifier.create(books)
                .assertNext(book -> {
                    assertEquals("Book1",book.getBookInfo().getTitle());
                    assertEquals(4L,book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book2",book.getBookInfo().getTitle());
                    assertEquals(4L,book.getReviews().size());
                })
                .assertNext(book -> {
                    assertEquals("Book3",book.getBookInfo().getTitle());
                    assertEquals(4L,book.getReviews().size());
                })
                .verifyComplete();
    }
}