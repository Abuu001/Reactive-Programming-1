package com.example.reactive_programming_tut1.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private BookInfo bookInfo;
    private List<Review> reviews;
}
