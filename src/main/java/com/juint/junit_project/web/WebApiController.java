package com.juint.junit_project.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.juint.junit_project.service.BookService;
import com.juint.junit_project.web.dto.req.BookListRespDto;
import com.juint.junit_project.web.dto.req.BookSaveDto;
import com.juint.junit_project.web.dto.res.BookResDto;
import com.juint.junit_project.web.dto.res.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WebApiController {

    private final BookService bookService;

    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResDto bookResDto = bookService.save(dto);
        CMRespDto<?> cmRespDto = CMRespDto.builder().code(1).msg("글 저장 성공").body(bookResDto).build();

        return new ResponseEntity<>(cmRespDto, HttpStatus.CREATED);
    }

    // 2. 책 목록
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookList = bookService.findAll();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookList).build(), HttpStatus.OK);
    }

    // 3. 책 상세
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookDteail(@PathVariable Long id) {
        BookResDto book = bookService.findById(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(book).build(), HttpStatus.OK);
    }

    // 4. 책 수정
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookUpdate(@PathVariable Long id, @RequestBody @Valid BookSaveDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }

        BookResDto book = bookService.updateById(id, dto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 수정 성공").body(book).build(), HttpStatus.OK);
    }

    // 5. 책 삭제
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookDelete(@PathVariable Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 삭제 성공").body(null).build(), HttpStatus.OK);
    }
}
