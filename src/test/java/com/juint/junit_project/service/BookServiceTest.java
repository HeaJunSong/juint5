package com.juint.junit_project.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    // 3. 책 상세
    // 4. 책 수정
    // 5. 책 삭제
}
