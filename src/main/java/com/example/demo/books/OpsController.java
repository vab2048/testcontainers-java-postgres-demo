package com.example.demo.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Useful operations for demos.
 */
@RestController
public class OpsController {
    public final Logger log = LoggerFactory.getLogger(BooksController.class);

    private final List<String> bookNames;
    private final BookRepository bookRepository;

    public OpsController(BookRepository bookRepository) throws IOException {
        this.bookRepository = bookRepository;

        // Initialise the book names
        InputStream inputStream = new ClassPathResource("/books.txt").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            bookNames = reader.lines().collect(Collectors.toList());
        }
        log.info("Books: {}", bookNames);
    }

    @GetMapping("/populate-with-entries")
    public void populate() throws IOException {
        for(String bookName : bookNames) {
            var book = new Book(bookName);
            bookRepository.save(book);
        }
    }

}
