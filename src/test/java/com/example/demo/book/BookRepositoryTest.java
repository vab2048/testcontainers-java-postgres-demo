package com.example.demo.book;

import com.example.demo.books.Book;
import com.example.demo.books.BookRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
public class BookRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(BookRepositoryTest.class);

    @Container
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:12.3")
            .withUsername("testcontainers")
            .withPassword("testcontainers")
            .withInitScript("db/migration/INIT_SCHEMA.sql")
            .withDatabaseName("testcontainers");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        log.info("Dynamically registering db for application context: {}", db.getJdbcUrl());
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    void book_WhenSaved_IsPersistedInDbSuccessfully() {
        log.info("Books before persisting: {}", bookRepository.findAll());

        /* Given: Our app environment is running, and we want to persist a book */
        var bookToPersist = new Book("1984");

        /* When: We save the book to the DB. */
        Book savedBook = bookRepository.save(bookToPersist);
        Long id = savedBook.getId();

        /* Then: The book can be retrieved from the DB. */
        Book retrievedBook = bookRepository.findById(id).orElseThrow();
        assertThat(retrievedBook.getId()).isEqualTo(id);
        assertThat(retrievedBook.getName()).isEqualTo(bookToPersist.getName());
        assertThat(retrievedBook.getStatus()).isEqualTo(bookToPersist.getStatus());

        log.info("Books after persisting: {}", bookRepository.findAll());
    }

}
