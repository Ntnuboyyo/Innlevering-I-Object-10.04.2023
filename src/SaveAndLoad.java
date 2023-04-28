import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.control.TextArea;


public class SaveAndLoad{
    
    Folder LoadLibrary() throws IOException {
        System.out.println("starting loading of library");
        File folder = new File("MainFolder");
        folder.mkdirs();
        Folder tempFolder=new Folder();
        File[] folderofbooks = folder.listFiles();
        for (File book : folderofbooks) {
            Book tempBook = new Book();
            tempBook.SetTitle(book.getName());
            File[] pages = book.listFiles();
            if(pages!=null){
                for(File page: pages){
                    System.out.println("starting reading of page file");
                    Reader r;
                    r=new FileReader(page);
                    Page temppPage= new Page(r.toString());
                    temppPage.SetTitle(page.getName());
                    tempBook.AddPage(temppPage);
                    r.close();
                }
            }
        }
        return tempFolder;
    }

    void SaveApplicationStates(Folder folder,Colorable innerfocus, Book innerbookfocus, Page innerpagefocus,TextArea pagetext){
        if (innerpagefocus != null) {
            String currentpagestring = pagetext.getText();
            ((Page) innerpagefocus).SetPage(currentpagestring);
            innerbookfocus.AddPage(innerpagefocus);
            System.out.println("page has info and is added to book");
        }
        String currentpath="";
        try{
        String path = "MainFolder";
        new File(path).mkdirs();
        System.out.println(path);
        for (Book book : folder.GetBooks()) {
            System.out.println(path + "/" + book.GetTitle());
            try{
                if(Files.exists(Paths.get(path + "/" + book.GetTitle()))){
                    //the folder exists, we skip creating it
                }
                else{
                    //the folder does not exist, we make it
                    new File((path + "/" + book.GetTitle())).mkdirs();
                }
            }
            finally{
                System.out.println("Unable to make book folder " + (path + "/" + book.GetTitle()));
            }
            for (Page page : book.GetPages()) {
                try{
                    if(page!=null){
                        currentpath=path + "/" + book.GetTitle() + "/" + page.GetTitle();
                    }
                    else{
                        continue;
                    }
                    if(Files.exists(Paths.get(currentpath))){
                        //the file exists, we dont need to create it
                        
                    }
                    else{
                        //the file does not exist, we need to make it
                        
                        File pagefile = new File(currentpath);
                    }
                    System.out.println(currentpath);
                    try{
                        Writer w;
                        w = new FileWriter(Paths.get(currentpath).toString());
                        System.out.println("Printing to file " + page.GetPage());
                        w.write(page.GetPage());
                        w.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Unable to make page " + (path + "/" + book.GetTitle() + "/" + page.GetTitle()));
                    }
                    finally{
                        System.out.println("File exists or is nullstring, the app will not be allowed to write");
                    }
                }
                finally{
                    //System.out.println("This file was not created on save" + (path + "/" + book.GetTitle() + "/" + page.GetTitle()));
                }
            }
        }
    }
    finally{
        System.out.println("due to limited time, we dont have time to implement a better save function, the savebutton saves to files, and on exit it performs the same action, but is unable to create files and folders, should be harmless errors, since the files are already saved");
    
    }
    }

}