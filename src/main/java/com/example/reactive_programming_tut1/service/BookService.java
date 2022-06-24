package com.example.reactive_programming_tut1.service;

import com.example.reactive_programming_tut1.domain.Book;
import com.example.reactive_programming_tut1.domain.Review;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService,
                       ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks(){
        var allBooks = bookInfoService.getBooks();
        return allBooks.flatMap(bookInfo -> {
          Mono<List<Review>> reviews =  reviewService.getReviews(bookInfo.getBookId()).collectList();
          return reviews
                  .map(reviews1 ->  new Book(bookInfo,reviews1));
        });
    }
}
