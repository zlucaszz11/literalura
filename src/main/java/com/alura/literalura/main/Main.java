package com.alura.literalura.main;

import com.alura.literalura.model.*;
import com.alura.literalura.service.BookService;
import com.alura.literalura.service.ConsumingApi;
import com.alura.literalura.service.ConvertData;

import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumingApi consumingApi = new ConsumingApi();
    private final ConvertData converter = new ConvertData();
    private final BookService bookService;

// CONSTRUCTOR
    public Main(BookService bookService) {
        this.bookService = bookService;
    }

    // METHOD
    public void getMenu() {
        String menu = """
                ***********************************************
                    Choose your option by it's number:
                    1 - Add a book to the library
                    2 - Show list of books registered
                    3 - Show list of authors registered
                    4 - Show list of authors that were/are alive in ... year
                    5 - Show list of books in ... language
                
                    0 - Exit
                ***********************************************
                """;
        int option = -1;

        while ( option != 0 ) {
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();

            switch ( option ) {
                case 1:
                    getBook();
                    break;
                case 2:
                    getAllBooks();
                    break;
                case 3:
                    getAllAuthors();
                    break;
                case 4:
                    getAuthorsAliveIn();
                    break;
                case 5:
                    getBooksByLanguage();
                    break;
                case 0:
                    System.out.println("bye bye!");
                    break;
                default:
                    System.out.println("Invalid option, try again...");
            }
        }

    }

// CASE 1
    private BookData getBookData() {
        String apiUrlStart = "https://gutendex.com//books?search=";
        System.out.println("Insert the name of the book: ");
        String bookTitle = scanner.nextLine();

        var bookJson = consumingApi.getApiData(apiUrlStart + bookTitle.toLowerCase().replace(" ", "%20"));
        ResultsData resultsData = converter.getData(bookJson, ResultsData.class);
        BookData bookData = resultsData.results().get(0);

        return bookData;
    }
    private void getBook() {
        BookData bookData = getBookData();
        Book book = new Book(bookData);

        // getting the first author in the array inside bookData and putting him into the Author class
        var firstAuthor = bookData.authors().get(0);
        AuthorData authorData = new AuthorData(firstAuthor.name(), firstAuthor.birth_year(), firstAuthor.death_year());
        Author author = new Author(authorData);

        book.setAuthor(author);
        bookService.saveBook(book);
        System.out.println(book);
    }

// CASE 2
    private void getAllBooks() {
        var books = bookService.getAllBooks();
        if(!books.isEmpty()) {
            books.forEach(System.out::println);
        } else {
            System.out.println("You haven't registered any books, please go to option 1 and register one");
        }
    }

// CASE 3
    private void getAllAuthors() {
        var authors = bookService.getAllAuthors();
        if(!authors.isEmpty()) {
            authors.forEach(System.out::println);
        } else {
            System.out.println("You haven't registered any author, please go to option 1 and register a book to get the author");
        }
    }

// CASE 4
    private void getAuthorsAliveIn() {
        System.out.println("Insert the year you want to search: ");
        String year = scanner.nextLine();

        var authorsAliveIn = bookService.getAuthorsAliveIn(year);
        if(!authorsAliveIn.isEmpty()) {
            authorsAliveIn.forEach(System.out::println);
        } else {
            System.out.println("No registered author was alive this year");
        }
    }

// CASE 5
    private void getBooksByLanguage() {
        var booksByLanguage = bookService.getBooksByLanguage();
        booksByLanguage.forEach(System.out::println);
    }
}
