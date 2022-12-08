package com.juint.junit_project.web.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.juint.junit_project.domain.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Controller에서 Setter가 호출되면서 Dto에 값이 재워짐.
public class BookSaveDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
