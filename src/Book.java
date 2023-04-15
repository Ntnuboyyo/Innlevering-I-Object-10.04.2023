import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;

public class Book implements PageContainer,Taggable,Colorable{
    Collection<Tag> tags = new ArrayList<Tag>();
    private Color color;
    private Collection<Page> pagearray = new ArrayList<Page>();
    public Collection<Page> GetPages(){
        return pagearray;
    }
    @Override
    public void SetColor(Color c){
        color=c;
    }
    @Override
    public Color GetColor(){
        return color;
    }
    public Collection<Page> SearchPagesString(String search){
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
    public void RegisterTag(Tag tag) {                          //let the book know it is tagged
        this.tags.add(tag);
    }
    @Override
    public Collection<Page> SearchPagesTag(Tag tag) {           //search for pages by tag
        return tag.pages;
    }
}
