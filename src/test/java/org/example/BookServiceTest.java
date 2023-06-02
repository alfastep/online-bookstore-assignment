package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookServiceTest {

    private BookService bookService;

    @Before
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void testSearchBook_ByTitle() {
        // Arrange
        String keyword = "Harry Potter";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        expectedBooks.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));

        // Act
        List<Book> result = bookService.searchBook(keyword);

        // Assert
        assertEquals(expectedBooks.size(), result.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            Book expectedBook = expectedBooks.get(i);
            Book resultBook = result.get(i);
            assertEquals(expectedBook.getTitle(), resultBook.getTitle());
            assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
            assertEquals(expectedBook.getGenre(), resultBook.getGenre());
            assertEquals(expectedBook.getPrice(), resultBook.getPrice(), 0.01);
        }
    }

    @Test
    public void testSearchBook_ByAuthor() {
        // Arrange
        String keyword = "J.K. Rowling";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        expectedBooks.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));

        // Act
        List<Book> result = bookService.searchBook(keyword);

        // Assert
        assertEquals(expectedBooks.size(), result.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            Book expectedBook = expectedBooks.get(i);
            Book resultBook = result.get(i);
            assertEquals(expectedBook.getTitle(), resultBook.getTitle());
            assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
            assertEquals(expectedBook.getGenre(), resultBook.getGenre());
            assertEquals(expectedBook.getPrice(), resultBook.getPrice(), 0.01);
        }
    }

    @Test
    public void testSearchBook_ByGenre() {
        // Arrange
        String keyword = "Fantasy";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        expectedBooks.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 19.99));
        bookService.addBook(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "Fantasy", 19.99));

        // Act
        List<Book> result = bookService.searchBook(keyword);

        // Assert
        assertEquals(expectedBooks.size(), result.size());
        for (int i = 0; i < expectedBooks.size(); i++) {
            Book expectedBook = expectedBooks.get(i);
            Book resultBook = result.get(i);
            assertEquals(expectedBook.getTitle(), resultBook.getTitle());
            assertEquals(expectedBook.getAuthor(), resultBook.getAuthor());
            assertEquals(expectedBook.getGenre(), resultBook.getGenre());
            assertEquals(expectedBook.getPrice(), resultBook.getPrice(), 0.01);
        }
    }

    @Test
    public void testPurchaseBook_BookAvailable() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        Book book = new Book("Book 1", "Author 1", "Genre 1", 9.99);
        bookService.addBook(book);

        // Act
        boolean result = bookService.purchaseBook(user, book);
        bookService.removeBook(book);
        List<Book> searchResults = bookService.searchBook("Book 1");
        // Assert
        assertTrue(result);
        assertFalse(searchResults.contains(book));
    }


    @Test
    public void testPurchaseBook_BookNotAvailable() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        Book book = new Book("Book 1", "Author 1", "Genre 1", 9.99);

        // Act
        boolean result = bookService.purchaseBook(user, book);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testAddBookReview_BookExists() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        Book book = new Book("Book 1", "Author 1", "Genre 1", 9.99);
        bookService.addBook(book);
        String review = "Great book! Highly recommended.";

        // Act
        bookService.purchaseBook(user, book);
        bookService.addBookReview(user, book, review);

        // Assert
        assertTrue(bookService.getBookDatabase().contains(book));
        assertTrue(book.getReviews().contains(review));
    }


    @Test
    public void testAddBookReview_BookDoesNotExist() {
        // Arrange
        User user = new User("john", "password", "john@example.com");
        Book book = new Book("Book 1", "Author 1", "Genre 1", 9.99);
        String review = "Great book! Highly recommended.";

        // Act
        bookService.addBookReview(user, book, review);

        // Assert
        assertFalse(bookService.getBookDatabase().contains(book));
    }

}
