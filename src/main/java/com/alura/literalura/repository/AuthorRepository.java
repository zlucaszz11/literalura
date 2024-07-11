package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNameContainingIgnoreCase(String name);
    @Query("SELECT a FROM Author a WHERE a.death_year >= :year AND :year >= a.birth_year")
    List<Author> authorsAliveIn(String year);
}
