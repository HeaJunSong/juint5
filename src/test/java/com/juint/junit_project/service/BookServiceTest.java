package com.juint.junit_project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.util.MailSender;
import com.juint.junit_project.web.dto.req.BookListRespDto;
import com.juint.junit_project.web.dto.req.BookSaveDto;
import com.juint.junit_project.web.dto.res.BookResDto;

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
        BookSaveDto dto = new BookSaveDto();
        dto.setTitle("junit5");
        dto.setAuthor("TEST");

        // stub (행동정의 가설작성한다.)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResDto bookPS = bookService.save(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookPS.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookPS.getAuthor());

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
        BookListRespDto bookListRespDto = bookService.findAll();

        // then
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo(books.get(0).getTitle());
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo(books.get(1).getTitle());
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo(books.get(1).getAuthor());
    }

    // 3. 책 상세
    @Test
    public void findById() {
        // given
        Long id = 1L;
        String title = "junit5";
        String author = "테스트";
        Book book = new Book(1L, title, author);
        Optional<Book> bookOP = Optional.of(book);

        // stub
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookResDto dto = bookService.findById(id);

        // then
        assertThat(dto.getTitle()).isEqualTo(book.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
    }

    // 4. 책 수정
    public void updateById() {
        // given
        Long id = 1L;
        String title = "junit5";
        String author = "테스트";
        BookSaveDto dto = new BookSaveDto();
        dto.setTitle(title);
        dto.setAuthor(author);

        // stub
        Book book = new Book(id, title, author);
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookResDto bookResDto = bookService.updateById(id, dto);

        // then
        assertThat(bookResDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookResDto.getAuthor()).isEqualTo(book.getAuthor());

    }
}
