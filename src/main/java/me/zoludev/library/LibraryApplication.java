package me.zoludev.library;

import me.zoludev.library.entity.BookEntity;
import me.zoludev.library.repo.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	//Just for test, from start saving book into database
	@Bean
	public CommandLineRunner loadData(BookRepository repository) {
		return args -> {
			repository.save(new BookEntity(1L, "Book Title 1", "Author 1", 2001, 4));
			repository.save(new BookEntity(2L, "Book Title 5", "Author 2", 2001, 3));
			repository.save(new BookEntity(3L, "Book Title 2", "Author 2", 2005, 5));
			repository.save(new BookEntity(4L, "Book Title 3", "Author 3", 1999, 3));
			repository.save(new BookEntity(5L, "Book Title 4", "Author 3", 1999, 3));
			repository.save(new BookEntity(6L, "Book Title 6", "Author 1", 2005, 3));
		};
	}
}
