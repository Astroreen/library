package me.zoludev.library.controller;

import me.zoludev.library.entity.BookEntity;
import me.zoludev.library.repo.BookRepository;
import me.zoludev.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class BooksAPIController {

    @Autowired
    private BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @GetMapping(path = "/filter")
    public Set<BookEntity> filter(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "rating", required = false) Integer rating,
            @RequestParam(value = "year", required = false) Integer year
    ) {

        if(title == null && author == null && rating == null && year == null) {
            return new HashSet<>(bookService.getAllBooks());
        }

        Set<BookEntity> books = new HashSet<>();

        if(title != null) books.addAll(new HashSet<>(bookService.filterByTitle(title)));
        if(author != null) books.addAll(new HashSet<>(bookService.filterByAuthor(author)));
        if(rating != null) books.addAll(new HashSet<>(bookService.filterByRating(rating)));
        if(year != null) books.addAll(new HashSet<>(bookService.filterByPublication(year)));

        books = books.stream()
                .filter(book -> title == null || book.getTitle().equalsIgnoreCase(title))
                .filter(book -> author == null || book.getAuthor().equalsIgnoreCase(author))
                .filter(book -> rating == null || book.getRating() == rating)
                .filter(book -> year == null || book.getYear() == year)
                .collect(Collectors.toSet());

        return books;
    }

    @PostMapping(path = "/rate")
    public String rateBook(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "rating") Integer rating
    ) {
        if(rating == null) return "Rated";
        if(rating < 0 || rating > 5) return "Can be rated from 0 to 5";
        bookService.rateBook(id, rating);
        return "Rated";
    }
}
