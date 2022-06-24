package com.example.reactive_programming_tut1.service;

import com.example.reactive_programming_tut1.domain.Review;
import java.util.List;
import reactor.core.publisher.Flux;

public class ReviewService {
    public Flux<Review> getReviews(Long bookId){
        var reviewList = List.of(
                new Review(1L,bookId,5.6,"Good"),
                new Review(2L,bookId,6.6,"Good"),
                new Review(3L,bookId,7.6,"Good"),
                new Review(4L,bookId,8.6,"Bad")
                );

        return  Flux.fromIterable(reviewList);
    }
}
