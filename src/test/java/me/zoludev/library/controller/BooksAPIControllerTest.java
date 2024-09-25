package me.zoludev.library.controller;

import me.zoludev.library.entity.BookEntity;
import me.zoludev.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BooksAPIControllerTest {

  @Mock
  private BookService bookService;

  @InjectMocks
  private BooksAPIController booksAPIController;

  private BookEntity book1;
  private BookEntity book2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Sample books
    book1 = new BookEntity();
    book1.setTitle("Spring Boot in Action");
    book1.setAuthor("Craig Walls");
    book1.setRating(5);
    book1.setYear(2016);

    book2 = new BookEntity();
    book2.setTitle("Effective Java");
    book2.setAuthor("Joshua Bloch");
    book2.setRating(5);
    book2.setYear(2018);
  }

  @Test
  void testFilterByTitle() {
    when(bookService.filterByTitle("Spring Boot in Action")).thenReturn(List.of(book1));

    Set<BookEntity> filteredBooks = booksAPIController.filter("Spring Boot in Action", null, null, null);

    assertEquals(1, filteredBooks.size());
    assertEquals("Spring Boot in Action", filteredBooks.iterator().next().getTitle());

    verify(bookService, times(1)).filterByTitle("Spring Boot in Action");
  }

  @Test
  void testFilterByAuthor() {
    when(bookService.filterByAuthor("Joshua Bloch")).thenReturn(List.of(book2));

    Set<BookEntity> filteredBooks = booksAPIController.filter(null, "Joshua Bloch", null, null);

    assertEquals(1, filteredBooks.size());
    assertEquals("Effective Java", filteredBooks.iterator().next().getTitle());

    verify(bookService, times(1)).filterByAuthor("Joshua Bloch");
  }

  @Test
  void testFilterByRating() {
    when(bookService.filterByRating(5)).thenReturn(List.of(book1, book2));

    Set<BookEntity> filteredBooks = booksAPIController.filter(null, null, 5, null);

    assertEquals(2, filteredBooks.size()); // Both books have rating 5

    verify(bookService, times(1)).filterByRating(5);
  }

  @Test
  void testFilterByYear() {
    when(bookService.filterByPublication(2018)).thenReturn(List.of(book2));

    Set<BookEntity> filteredBooks = booksAPIController.filter(null, null, null, 2018);

    assertEquals(1, filteredBooks.size());
    assertEquals("Effective Java", filteredBooks.iterator().next().getTitle());

    verify(bookService, times(1)).filterByPublication(2018);
  }

  @Test
  void testFilterByMultipleCriteria() {
    when(bookService.filterByTitle("Spring Boot in Action")).thenReturn(List.of(book1));
    when(bookService.filterByAuthor("Craig Walls")).thenReturn(List.of(book1));
    when(bookService.filterByRating(5)).thenReturn(List.of(book1, book2));
    when(bookService.filterByPublication(2016)).thenReturn(List.of(book1));

    Set<BookEntity> filteredBooks = booksAPIController.filter("Spring Boot in Action", "Craig Walls", 5, 2016);

    assertEquals(1, filteredBooks.size());
    assertEquals("Spring Boot in Action", filteredBooks.iterator().next().getTitle());

    verify(bookService, times(1)).filterByTitle("Spring Boot in Action");
    verify(bookService, times(1)).filterByAuthor("Craig Walls");
    verify(bookService, times(1)).filterByRating(5);
    verify(bookService, times(1)).filterByPublication(2016);
  }

  @Test
  void testFilterWithoutCriteria() {
    when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

    Set<BookEntity> filteredBooks = booksAPIController.filter(null, null, null, null);

    assertEquals(2, filteredBooks.size()); // No filtering applied, return all books

    verify(bookService, times(1)).getAllBooks();
  }

  @Test
  void testRateBookWithinValidRange() {
    String response = booksAPIController.rateBook(1, 4);

    assertEquals("Rated", response);
    verify(bookService, times(1)).rateBook(1, 4);
  }

  @Test
  void testRateBookWithInvalidRating() {
    String response = booksAPIController.rateBook(1, 6);

    assertEquals("Can be rated from 0 to 5", response);
    verify(bookService, times(0)).rateBook(anyInt(), anyInt()); // No rating should occur
  }

  @Test
  void testRateBookWithNullRating() {
    String response = booksAPIController.rateBook(1, null);

    assertEquals("Rated", response);
    verify(bookService, times(0)).rateBook(anyInt(), anyInt()); // No rating should occur
  }
}
