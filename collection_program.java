import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isIssued;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issueBook() {
        this.isIssued = true;
    }

    public void returnBook() {
        this.isIssued = false;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", ISBN='" + isbn + '\'' +
                ", IsIssued=" + isIssued +
                '}';
    }
}

class Library {
    private HashMap<String, Book> booksByIsbn;  // Store books by ISBN
    private HashMap<String, List<Book>> booksByAuthor;  // Store books by Author
    private HashSet<Book> issuedBooks;  // Store issued books
    private LinkedList<Book> recentlyReturnedBooks;  // Store history of recently returned books

    public Library() {
        booksByIsbn = new HashMap<>();
        booksByAuthor = new HashMap<>();
        issuedBooks = new HashSet<>();
        recentlyReturnedBooks = new LinkedList<>();
    }

    // Add book to the library
    public void addBook(Book book) {
        booksByIsbn.put(book.getIsbn(), book);

        // Add book to author collection
        booksByAuthor.putIfAbsent(book.getAuthor(), new ArrayList<>());
        booksByAuthor.get(book.getAuthor()).add(book);
    }

    // Issue book by ISBN
    public void issueBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book != null && !book.isIssued()) {
            book.issueBook();
            issuedBooks.add(book);
            System.out.println("Book issued: " + book.getTitle());
        } else {
            System.out.println("Book is either not available or already issued.");
        }
    }

    // Return issued book by ISBN
    public void returnBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book != null && book.isIssued()) {
            book.returnBook();
            issuedBooks.remove(book);
            recentlyReturnedBooks.addFirst(book);  // Add to history of returned books
            System.out.println("Book returned: " + book.getTitle());
        } else {
            System.out.println("Book is either not issued or doesn't exist.");
        }
    }

    // Search books by author
    public void searchBooksByAuthor(String author) {
        List<Book> books = booksByAuthor.get(author);
        if (books != null && !books.isEmpty()) {
            System.out.println("Books by " + author + ":");
            for (Book book : books) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books found by this author.");
        }
    }

    // View issued books
    public void viewIssuedBooks() {
        System.out.println("Issued Books:");
        for (Book book : issuedBooks) {
            System.out.println(book);
        }
    }

    // View recently returned books
    public void viewRecentlyReturnedBooks() {
        System.out.println("Recently Returned Books:");
        for (Book book : recentlyReturnedBooks) {
            System.out.println(book);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add some books to the library
        library.addBook(new Book("Java Fundamentals", "James Gosling", "ISBN001"));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "ISBN002"));
        library.addBook(new Book("Clean Code", "Robert Martin", "ISBN003"));
        library.addBook(new Book("Design Patterns", "Erich Gamma", "ISBN004"));

        // Issue some books
        library.issueBook("ISBN002");
        library.issueBook("ISBN003");

        // Return a book
        library.returnBook("ISBN002");

        // Search books by author
        library.searchBooksByAuthor("James Gosling");
        library.searchBooksByAuthor("Robert Martin");

        // View issued books
        library.viewIssuedBooks();

        // View recently returned books
        library.viewRecentlyReturnedBooks();
    }
}
