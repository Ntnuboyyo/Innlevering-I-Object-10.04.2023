import java.security.InvalidParameterException;
import java.util.ArrayList;

import javafx.scene.paint.Color;


public class Page implements Taggable,Colorable {
    private Color[] color = new Color[2];
    private String title="";
    private ArrayList<Tag> tags = new ArrayList<Tag>();
    public static int last_page_id=-1;      //-1 to allow the pid to be used as an index, PID() increments to 0 on first page
    private int pid;                        //page id
    private String page;                    //contains the characters on the page
    Page(String pagestring){
        page=pagestring;
        pid=PID();
        SetColor(Color.WHITE, Color.WHITE);
    }
    public String GetPage(){
        return page;                        //a method for retriving the page string out of the object
    }
    public void SetPage(String p){
        page = p;                           //a method for overwriting the page
    }
    public String GetTitle(){
        return title;
    }
    public void SetTitle(String t){
        if(t==""){throw new InvalidParameterException("page must have a title with characters to be able to save to file");}
        if(t.contains(".")){throw new InvalidParameterException("dangerus . in title of a page, might cause problems on save");}
        title = t;
    }
    public int GetLength(){
        return page.length();               //the number of characters on the page
    }
    public int GetWordCount(){
        return page.split(" ").length;     //the length of the list of words
    }
    private int PID(){                      //assigning an integer to each page, might use for search functionality?
        last_page_id++;
        return last_page_id;
    }
    public void RegisterTag(Tag tag) {
        if(tag==null){throw new InvalidParameterException();}
        this.tags.add(tag);
    }
    public int GetPID(){
        return pid;
    }
    @Override
    public void SetColor(Color a,Color b){
        if(a!=null){color[0]=a;}
        else if(b!=null){color[1]=b;}
        else{
            throw new InvalidParameterException();}
        }
    
    @Override
    public Color[] GetColor(){
        return color;
    }
}
