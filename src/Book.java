import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;

public class Book implements PageContainer,Taggable,Colorable{
    private Collection<Tag> tags = new ArrayList<Tag>();
    private String title="";
    private Color[] color = new Color[2];
    private Collection<Page> pagearray = new ArrayList<Page>();
    public static int last_book_id=-1;      //-1 to allow the pid to be used as an index, PID() increments to 0 on first page
    private int bid;                        //book id
    public Collection<Page> GetPages(){
        return pagearray;
    }
    public ArrayList<Book> GetAllBooks(){
        return Folder.allbooks;
    }
    Book(){
        Folder.allbooks.add(this);
        bid=BID();
        SetColor(Color.WHITE, Color.WHITE);
    }
    void DeleteBook(Book book){
        Folder.allbooks.remove(book);
    }
    public String GetTitle(){
        return title;
    }
    public void SetTitle(String t){
        title = t;
    }
    public void DeletePage(Page page){
        this.pagearray.remove(page);
    }
    @Override
    public void SetColor(Color a,Color b){
        if(a!=null){color[0]=a;}
        if(b!=null){color[1]=b;}
    }
    @Override
    public Color[] GetColor(){
        return color;
    }
    private int BID(){                      //assigning an integer to each page, might use for search functionality? only ever used on instanciation
        last_book_id++;
        return last_book_id;
    }
    public Collection<Page> SearchPagesString(String search){
        if(search==""){return null;};
        Collection<Page> positives= new ArrayList<Page>();      //an array to store the positives in the search
        for(Page page : pagearray){                             //loop over the pages
            if(page.GetPage().contains(search)){                //if the page contains the string
                positives.add(page);                            //add the page to the array of positives
            }
        }
        return positives;                                       //return the positives
    }
    public int PageCount(){                                     //the amount of pages in the book
        return pagearray.size();                                //return the length of the array of pages
    }
    public int WordCount(){                                     //the amount of words in the book
        int words=0;
        for (Page page : pagearray){
            words+=page.GetWordCount();
        }
        return words;
    }
    public void RegisterTag(Tag tag) {   
        if(tag==null){throw new InvalidParameterException();}                       //let the book know it is tagged
        this.tags.add(tag);
    }
    @Override
    public Collection<Page> SearchPagesTag(Tag tag) {           //search for pages by tag
        return tag.pages;
    }
    public int GetBID(){
        return bid;
    }
    public void AddPage(Page innerpage) {
        pagearray.add(innerpage);
    }
}
