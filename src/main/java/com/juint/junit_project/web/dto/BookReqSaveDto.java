package com.juint.junit_project.web.dto;

import com.juint.junit_project.domain.Book;

import lombok.Setter;

@Setter // Controller에서 Setter가 호출되면서 Dto에 값이 재워짐.
public class BookReqSaveDto {
    private String title;
    private String autho;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(autho)
                .build();
    }
}
