import java.util.ArrayList;

public interface BookContainer {
    static ArrayList<Book> allbooks = new ArrayList<Book>();
    ArrayList<Book> GetBooks();
    ArrayList<Book> SearchBooksString(String search);
    ArrayList<Book> SearchBooksTag(Tag tag);
    int PageCount();
    int WordCount();
    static void AddBook(Book book){
        allbooks.add(book);
    };
    void RemoveBook(Book book);
}
