package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book getByTitle(String title);
    @Query("SELECT DISTINCT b.language FROM Book b ORDER BY b.language")
    List<String> languages();
    @Query("SELECT b FROM Book b WHERE language = :language")
    List<Book> booksByLanguage(String language);
}
