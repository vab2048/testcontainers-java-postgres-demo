package com.example.demo.books;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class BooksController {
    public final Logger log = LoggerFactory.getLogger(BooksController.class);

    private final BookRepository bookRepository;

    public BooksController(BookRepository bookRepository) throws IOException {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return IteratorUtils.toList(bookRepository.findAll().iterator());
    }


    @GetMapping("/books/{id}")
    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }


    public record PostBookRequest(String bookName) {}

    @PostMapping("/books")
    public Book postBook(PostBookRequest request) {
        var book = new Book(request.bookName());
        return bookRepository.save(book);
    }

}
