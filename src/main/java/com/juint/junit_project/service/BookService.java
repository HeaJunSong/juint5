package com.juint.junit_project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.util.MailSender;
import com.juint.junit_project.web.dto.req.BookListRespDto;
import com.juint.junit_project.web.dto.req.BookSaveDto;
import com.juint.junit_project.web.dto.res.BookResDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 쓰기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto save(BookSaveDto dto) {
        Book book = bookRepository.save(dto.toEntity());
        if (book != null) {
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
            return book.toDto();
        } else {
            return null;
        }
    }

    // 2. 책 목록
    public BookListRespDto findAll() {
        List<BookResDto> dtos = bookRepository.findAll().stream()
                .map(Book::toDto)
                .collect(Collectors.toList());

        dtos.stream().forEach((b) -> {
            System.out.println(b.getId());
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor());
            System.err.println("=================== 서비스 레이어");
        });

        BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();
        return bookListRespDto;
    }

    // 3. 책 상세
    public BookResDto findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            Book bookPS = book.get();
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을수 없습니다.");
        }
    }

    // 4. 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto updateById(Long id, BookSaveDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을수 없습니다.");
        }
    }
}
