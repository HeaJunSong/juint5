package com.juint.junit_project.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩한다.
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        System.out.println("책등록_test 실행");
        // given (데이터 준비)
        String title = "junit5 테스트";
        String autho = "테스트";
        Book book = Book.builder()
                .title(title)
                .author(autho)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        System.out.println("=======================================================================");

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(autho, bookPS.getAuthor());

        System.out.println(bookPS.getTitle());

        System.out.println("=======================================================================");
    }

    // 2. 책 목록보기
    // 3. 책 상세
    // 4. 책 수정
    // 5. 책 삭제

}
