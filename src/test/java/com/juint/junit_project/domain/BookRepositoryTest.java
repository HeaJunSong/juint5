package com.juint.junit_project.domain;

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
        // Book book;
        // book.builder();
        // bookRepository.save(book);

    }
    // 2. 책 목록보기
    // 3. 책 상세
    // 4. 책 수정
    // 5. 책 삭제

}
