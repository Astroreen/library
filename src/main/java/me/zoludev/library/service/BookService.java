package me.zoludev.library.service;

import me.zoludev.library.entity.BookEntity;
import me.zoludev.library.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookEntity> filterByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<BookEntity> filterByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<BookEntity> filterByPublication(int publication) {
        return bookRepository.findByPublication(publication);
    }

    public List<BookEntity> filterByRating(int rating) {
        return bookRepository.findByRating(rating);
    }

    public BookEntity rateBook(long id, int rating) {
        BookEntity book = bookRepository.findById(id).orElseThrow();
        book.setRating(rating);
        return bookRepository.save(book);
    }
}
