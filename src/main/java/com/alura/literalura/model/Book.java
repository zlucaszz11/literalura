package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String authorName;
    private String language;
    private Integer download_count;
    @ManyToOne
    private Author author;

// CONSTRUCTOR
    public Book() {}
    public Book(BookData bookData) {
        this.title = bookData.title();
        this.authorName = bookData.authors().get(0).name();
        this.language = String.join(",", bookData.languages());
        this.download_count = bookData.download_count();
    }


// GETTERS
    public String getTitle() {return title;}
    public String getLanguage() {return language;}
    public Integer getDownload_count() {return download_count;}
    public String getAuthorName() {return authorName;}
    public Author getAuthor() {return author;}

// SETTERS
    public void setAuthor(Author author) {this.author = author;}


// TO STRING
    @Override
    public String toString() {
        return "Book: {" + '\n' +
                "   title= " + title + '\n' +
                author + '\n' +
                "   language(s)= " + language + '\n' +
                "   download count= " + download_count + '\n' + "}";
    }
}
