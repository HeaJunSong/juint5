package com.juint.junit_project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.util.MailSender;
import com.juint.junit_project.web.dto.BookReqSaveDto;
import com.juint.junit_project.web.dto.BookResDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    // 1. 책 등록
    @Test
    public void save() {
        // given
        BookReqSaveDto dto = new BookReqSaveDto();
        dto.setTitle("junit5");
        dto.setAutho("TEST");

        // stub (행동정의 가설작성한다.)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResDto bookPS = bookService.save(dto);

        // then
        // assertEquals(dto.getTitle(), bookPS.getTitle());
        // assertEquals(dto.getAutho(), bookPS.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookPS.getTitle());
        assertThat(dto.getAutho()).isEqualTo(bookPS.getAuthor());

    }

    // 2. 책 목록
    @Test
    public void findAll() {
        // given

        // stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "test1", "11"));
        books.add(new Book(2L, "test2", "22"));
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookResDto> bookPS = bookService.findAll();

        // then
        assertThat(bookPS.get(0).getTitle()).isEqualTo(books.get(0).getTitle());
        assertThat(bookPS.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(bookPS.get(1).getTitle()).isEqualTo(books.get(1).getTitle());
        assertThat(bookPS.get(1).getAuthor()).isEqualTo(books.get(1).getAuthor());
    }

    // 3. 책 상세
    // 4. 책 수정
    // 5. 책 삭제
}
