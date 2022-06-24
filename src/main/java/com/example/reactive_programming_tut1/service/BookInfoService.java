package com.example.reactive_programming_tut1.service;

import com.example.reactive_programming_tut1.domain.BookInfo;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BookInfoService {
    public Flux<BookInfo> getBooks(){
        var books = List.of(
                new BookInfo(1L,"Book1","Author1","2131313"),
                new BookInfo(2L,"Book2","Author2","2131213"),
                new BookInfo(3L,"Book3","Author3","2131413"),
                new BookInfo(4L,"Book4","Author4","2131513")
                );
        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(Long id){
        var book = new BookInfo(id,"Book1","Author1","2131313");
        return Mono.just(book);
    }
}
