package com.juint.junit_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.util.MailSenderStub;
import com.juint.junit_project.web.dto.BookReqSaveDto;
import com.juint.junit_project.web.dto.BookResDto;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void save() {
        // given
        BookReqSaveDto dto = new BookReqSaveDto();
        dto.setTitle("junit5");
        dto.setAutho("TEST");

        // stub
        MailSenderStub stub = new MailSenderStub();
        BookService service = new BookService(bookRepository, stub);

        // when
        BookResDto bookPS = service.save(dto);

        // then
        assertEquals(dto.getTitle(), bookPS.getTitle());
        assertEquals(dto.getAutho(), bookPS.getAuthor());

    }
    // 2. 책 목록
    // 3. 책 상세
    // 4. 책 수정
    // 5. 책 삭제

}
