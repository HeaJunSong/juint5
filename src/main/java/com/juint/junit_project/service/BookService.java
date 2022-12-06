package com.juint.junit_project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.web.dto.BookReqSaveDto;
import com.juint.junit_project.web.dto.BookResDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 1. 책 쓰기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto save(BookReqSaveDto dto) {
        Book book = bookRepository.save(dto.toEntity());
        return new BookResDto().toDto(book);
    }

    // 2. 책 목록
    // 3. 책 상세
    // 4. 책 삭제
    // 5. 책 수정

}
