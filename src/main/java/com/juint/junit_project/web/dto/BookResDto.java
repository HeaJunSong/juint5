package com.juint.junit_project.web.dto;

import com.juint.junit_project.domain.Book;

import lombok.Getter;

@Getter
public class BookResDto {
    private Long id;
    private String title;
    private String author;

    public BookResDto toDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        return this;
    }

}
