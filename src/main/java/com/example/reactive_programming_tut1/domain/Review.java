package com.example.reactive_programming_tut1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long reviewId;
    private  Long bookId;
    private  Double ratings;
    private  String comments;
}
