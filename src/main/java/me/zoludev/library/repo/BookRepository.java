package me.zoludev.library.repo;

import me.zoludev.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);
    List<BookEntity> findByAuthor(String author);
    List<BookEntity> findByPublication(int publication);
    List<BookEntity> findByRating(int rating);
}
