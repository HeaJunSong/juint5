package com.juint.junit_project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.util.MailSender;
import com.juint.junit_project.web.dto.BookReqSaveDto;
import com.juint.junit_project.web.dto.BookResDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책 쓰기
    @Transactional(rollbackFor = RuntimeException.class)
    public BookResDto save(BookReqSaveDto dto) {
        Book book = bookRepository.save(dto.toEntity());
        if (book != null) {
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            } else {

            }
        }
        return new BookResDto().toDto(book);
    }

    // 2. 책 목록
    public List<BookResDto> findAll() {
        List<BookResDto> dtos = bookRepository.findAll().stream()
                .map((b) -> new BookResDto().toDto(b))
                .collect(Collectors.toList());

        dtos.stream().forEach((b) -> {
            System.out.println(b.getId());
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor());
            System.err.println("=================== 서비스 레이어");
        });

        return dtos;
    }

    // 3. 책 상세
    public BookResDto findById(Long id) {
        Book book = bookRepository.findById(id).get();
        if (book.isPresent()) {
            return new BookResDto().toDto(book);
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
    public BookResDto update(Long id, BookResDto dto) {
        Book book = bookRepository.findById(id).get();
        if (book.isPresent()) {
            book.update(dto.getTitle(), dto.getAuthor());
            return new BookResDto().toDto(bookRepository.save(book));
        } else {
            throw new RuntimeException("해당 아이디를 찾을수 없습니다.");
        }
    }

}
