import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;

public class Tag {
    Collection<Folder> folders = new ArrayList<Folder>();   //folders tagged with this tag
    Collection<Book> books = new ArrayList<Book>();         //books tagged with this tag
    Collection<Page> pages = new ArrayList<Page>();         //pages tagged with this tag
    String taglabel;
    Color color;
    Tag(Color color, String taglabel){                  //constructor, assign a color to the tag, and a label to display
        this.color = color;
        this.taglabel=taglabel;
    }
    void TagFolder(Folder folder){                      //inform tag and folder that they are linked
        this.folders.add(folder);
        AddTagToFolder(folder);
    }
    void TagBook(Book book){                            //inform tag and book that they are linked
        this.books.add(book);
        AddTagToBook(book);
    }
    void TagPage(Page page){                            //inform tag and page that they are linked
        this.pages.add(page);
        AddTagToPage(page);
    }
    public void AddTagToFolder(Folder folder) {         //inform folder that it is tagged
        folder.RegisterTag(this);
    }
    public void AddTagToBook(Book book) {               //inform book that it is tagged
        book.RegisterTag(this);
    }
    public void AddTagToPage(Page page) {               //inform page that it is tagged
        page.RegisterTag(this);
    }
}
