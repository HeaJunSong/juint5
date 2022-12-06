package com.juint.junit_project.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩한다.
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    // @BeforeAll // 테스트 시작전 한번만 실행
    @BeforeEach // 각 테스트 사작전에 한번씩 실행
    public void 데이터준비() {
        System.out.println("================================================================ 데이터 생성");
        String title = "junit5 테스트";
        String autho = "테스트";
        Book book = Book.builder()
                .title(title)
                .author(autho)
                .build();

        // when (테스트 실행)
        bookRepository.save(book);
    }// 트랜잭션 종료 됐다면 말이안됨
     // 가정 1 : [ 데이터준비() + 1 책등록 ] (T) , [ 데이터준비() + 2 책목록보기 ] (T) -> 사이즈 1 (검증 완료)
     // 가정 2 : [ 데이터준비() + 1 책등록 + 데이터준비() + 2 책목록보기 ] (T) -> 사이즈 2 (검증 실패)

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

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(autho, bookPS.getAuthor());

    } // 트랜잭션 종료(저장된 데이터를 초기화 함)

    // 2. 책 목록보기
    @Test
    public void 책목록보기_test() {
        // given (데이터 준비)
        String title = "junit5 테스트";
        String autho = "테스트";

        // when
        List<Book> books = bookRepository.findAll();
        System.out.println("======================================================      : " + books.size());

        // then
        assertEquals(title, books.get(0).getTitle());
        assertEquals(autho, books.get(0).getAuthor());
    }

    // 3. 책 한건보기
    @Test
    public void 책목상세_test() {
        // given
        String title = "junit5 테스트";
        String autho = "테스트";

        // when
        Book entBook = bookRepository.findById(1L).orElse(null);

        // then
        if (entBook == null) {
            assertEquals(null, entBook);
        } else {
            assertEquals(title, entBook.getTitle());
            assertEquals(autho, entBook.getAuthor());
        }

    }

    // 4. 책 수정
    // @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test() {
        // given
        Long id = 1L;
        String title = "junit5 테스트!!!!!";
        String author = "테스트!!!!!!!";
        Book book = new Book(id, title, author);

        // when
        // bookRepository.findAll().stream()
        // .forEach((b) -> {
        // System.out.println(b.getId());
        // System.out.println(b.getTitle());
        // System.out.println(b.getAuthor());
        // System.out.println("1.===================");
        // });

        Book bookPS = bookRepository.save(book);

        // bookRepository.findAll().stream()
        // .forEach((b) -> {
        // System.out.println(b.getId());
        // System.out.println(b.getTitle());
        // System.out.println(b.getAuthor());
        // System.out.println("2.===================");
        // });

        // then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 5. 책 삭제
    // @Sql("classpath:/db/tableInit.sql")
    @Test
    public void 책삭제_test() {
        // given
        Long id = 1L;

        System.out.println(id);

        // when
        // bookRepository.deleteById(id);

        // then
        // assertFalse(bookRepository.findById(id).isPresent());

    }

}
