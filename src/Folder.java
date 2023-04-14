import java.util.ArrayList;
import java.util.Collection;

public class Folder implements BookContainer, PageContainer, Taggable{
    Collection<Tag> tags = new ArrayList<Tag>();
    private Collection<Book> bookarray = new ArrayList<Book>();
    @Override
    public Collection<Page> GetPages() {
        Collection<Page> pagearray = new ArrayList<Page>();
        for(Book book : bookarray){
            for(Page page : book.GetPages()){
                pagearray.add(page);
            }
        }
        return pagearray;
    }

    @Override
    public Collection<Page> SearchPagesString(String search) {
        Collection<Page> positives = new ArrayList<Page>();
        for(Book book : bookarray){
            for (Page page : book.SearchPagesString(search)){
                positives.add(page);
            }

        }
        return positives;
    }

    @Override
    public Collection<Book> GetBooks() {
        return bookarray;
    }

    @Override
    public Collection<Book> SearchBooksString(String search) {
        Collection<Book> positives = new ArrayList<Book>();
        for(Book book : bookarray){
            for (Page page : book.GetPages()){
                if (page.GetPage().contains(search)){
                    positives.add(book);
                }
            }
        }
        return positives;
    }

    @Override
    public int PageCount() {
        int pagecount =0;
        for (Book book:bookarray){
            pagecount+=book.PageCount();
        }
        return pagecount;
    }

    @Override
    public int WordCount() {
        int wordcount =0;
        for (Book book:bookarray){
            wordcount+=book.WordCount();
        }
        return wordcount;
    }

    @Override
    public void AddBook(Book book) {
        bookarray.add(book);
    }

    @Override
    public void RemoveBook(Book book) {
        bookarray.remove(book);
    }

    @Override
    public void RegisterTag(Tag tag) {
        tag.TagFolder(null);
    }

    @Override
    public Collection<Page> SearchPagesTag(Tag tag) {
        return tag.pages;
    }
    @Override
    public Collection<Book> SearchBooksTag(Tag tag) {
        return tag.books;
    }
    
}
