import java.util.ArrayList;
import java.util.Collection;

public class Folder implements BookContainer, PageContainer, Taggable{
    Collection<Tag> tags = new ArrayList<Tag>();
    //private static Collection<Book> bookarray = new ArrayList<Book>();
    @Override
    public ArrayList<Page> GetPages() {
        ArrayList<Page> pagearray = new ArrayList<Page>();
        for(Book book : allbooks){
            for(Page page : book.GetPages()){
                pagearray.add(page);
            }
        }
        return pagearray;
    }

    @Override
    public ArrayList<Page> SearchPagesString(String search) {
        if(search==null){return null;};
        if(search==""){return null;};
        ArrayList<Page> positives = new ArrayList<Page>();
        for(Book book : allbooks){
            for (Page page : book.SearchPagesString(search)){
                positives.add(page);
            }

        }
        return positives;
    }

    @Override
    public ArrayList<Book> GetBooks() {
        return allbooks;
    }

    @Override
    public ArrayList<Book> SearchBooksString(String search) {
        if(search==null){return null;};
        if(search==""){return null;};
        ArrayList<Book> positives = new ArrayList<Book>();
        for(Book book : allbooks){
            for (Page page : book.GetPages()){
                if (page.GetPage().contains(search)){
                    positives.add(book);
                }
            }
        }
        return positives;
    }
    
    public Book SearchPagesBID(int searchbid) {           //search for pages by tag
        if(searchbid < 0){return null;};
        ArrayList<Book> searables = allbooks;
        for(Book book: searables){
            if (book.GetBID()==searchbid){
                return book;
            }
        }
        return null;
    }
    @Override
    public int PageCount() {
        int pagecount =0;
        for (Book book:allbooks){
            pagecount+=book.PageCount();
        }
        return pagecount;
    }

    @Override
    public int WordCount() {
        int wordcount =0;
        for (Book book:allbooks){
            wordcount+=book.WordCount();
        }
        return wordcount;
    }


    static void AddBook(Book book){
        allbooks.add(book);
    };
    @Override
    public void RemoveBook(Book book) {
        allbooks.remove(book);
    }

    @Override
    public void RegisterTag(Tag tag) {
        tag.TagFolder(null);
    }

    @Override
    public Collection<Page> SearchPagesTag(Tag tag) {
        if(tag==null){return null;};
        return tag.pages;
    }
    @Override
    public ArrayList<Book> SearchBooksTag(Tag tag) {
        if(tag==null){return null;};
        return tag.books;
    }
    
}
