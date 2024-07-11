package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Scanner;


@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    public void saveBook(Book book) {
        Book bookAlreadySaved = bookRepository.getByTitle(book.getTitle());

        if(bookAlreadySaved == null) {
            Author author = book.getAuthor();

            Author authorAlreadySaved = authorRepository.findByNameContainingIgnoreCase(author.getName());
            if(authorAlreadySaved != null) {
                book.setAuthor(authorAlreadySaved);
                authorAlreadySaved.setBooks(book);
            } else {
                Author savedAuthor = authorRepository.save(author);
                book.setAuthor(savedAuthor);
                savedAuthor.setBooks(book);
            }

            bookRepository.save(book);
            System.out.println("Book added successfully!!");
        } else {
            System.out.println("This book has already been entered");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthorsAliveIn(String year) {
        return authorRepository.authorsAliveIn(year);
    }

    public List<Book> getBooksByLanguage() {
        Scanner scanner = new Scanner(System.in);
        List<String> languages = bookRepository.languages();
        System.out.print("\nLanguages available: { ");
        if(languages.size() == 1) {
            languages.forEach(System.out::print);
        } else {
            languages.forEach(l -> System.out.print(l + ", "));
        }
        System.out.println(" }");
        System.out.println("\nChoose your language to search the books by: ");
        String language = scanner.nextLine();
        if(!languages.contains(language)) {
            System.out.println("No registered book uses this language");
        }
        return bookRepository.booksByLanguage(language);
    }
}
