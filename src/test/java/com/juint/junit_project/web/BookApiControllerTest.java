package com.juint.junit_project.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.juint.junit_project.domain.Book;
import com.juint.junit_project.domain.BookRepository;
import com.juint.junit_project.web.dto.req.BookSaveDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestRestTemplate rt;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        System.out.println("== ObjectMapper");
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void 데이터준비() {
        String title = "junit5";
        String author = "테스트";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        bookRepository.save(book);

        book = Book.builder()
                .title(title)
                .author(author)
                .build();

        bookRepository.save(book);
    }

    @Test
    public void saveBook_test() throws Exception {
        // given
        BookSaveDto bookSaveDto = new BookSaveDto();
        bookSaveDto.setTitle("제목1");
        bookSaveDto.setAuthor("이름1");

        String body = om.writeValueAsString(bookSaveDto);
        System.out.println(body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        System.out.println("===================================================");
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(bookSaveDto.getTitle());
        assertThat(author).isEqualTo(bookSaveDto.getAuthor());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookList_test() {
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);
        System.out.println("===================================================");
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.items[0].title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookOne_test() {
        // given
        Integer id = 2;

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.GET, request, String.class);
        System.out.println("===================================================");
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");

    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void updateBook_test() throws Exception {
        // given
        Integer id = 1;
        BookSaveDto bookSaveDto = new BookSaveDto();
        bookSaveDto.setTitle("제목1");
        bookSaveDto.setAuthor("이름1");

        String body = om.writeValueAsString(bookSaveDto);
        System.out.println(body);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.PUT, request, String.class);
        System.out.println("===================================================");
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(bookSaveDto.getTitle());
        assertThat(author).isEqualTo(bookSaveDto.getAuthor());
    }

}
