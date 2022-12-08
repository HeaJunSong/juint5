package com.juint.junit_project.web.dto.req;

import java.util.List;

import com.juint.junit_project.web.dto.res.BookResDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookListRespDto {
    List<BookResDto> items;

    @Builder
    public BookListRespDto(List<BookResDto> bookList) {
        this.items = bookList;
    }

}
