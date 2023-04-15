import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;


public class Page implements Taggable,Colorable {
    private Color color;
    Collection<Tag> tags = new ArrayList<Tag>();
    public static int last_page_id=-1;      //-1 to allow the pid to be used as an index, PID() increments to 0 on first page
    int pid;
    private String page;                    //contains the characters on the page
    Page(String pagestring){
        page=pagestring;
        pid=PID();
    }
    public String GetPage(){
        return page;                        //a method for retriving the page string out of the object
    }
    public int GetLength(){
        return page.length();               //the number of characters on the page
    }
    public int GetWordCount(){
        return page.split(page).length;     //the length of the list of words
    }
    private int PID(){                      //assigning an integer to each page, might use for search functionality?
        last_page_id++;
        return last_page_id;
    }
    public void RegisterTag(Tag tag) {
        this.tags.add(tag);
    }
    @Override
    public void SetColor(Color c) {
        color=c;
    }
    @Override
    public Color GetColor() {
        return color;
    }
}
