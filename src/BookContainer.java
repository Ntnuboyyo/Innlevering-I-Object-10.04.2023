import java.util.Collection;

public interface BookContainer {
    Collection<Book> GetBooks();
    Collection<Book> SearchBooksString(String search);
    Collection<Book> SearchBooksTag(Tag tag);
    int PageCount();
    int WordCount();
    void AddBook(Book book);
    void RemoveBook(Book book);
}
