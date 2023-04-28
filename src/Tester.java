import java.io.IOException;

import javafx.scene.paint.Color;

public class Tester {
    // testing strings with .txt instead of other file extensions, proof of concept
    void RunnAllTests() throws IOException {
        BookTest();
        PageTest();
        FolderTest();
        try{
            SaveAndLoad s = new SaveAndLoad();
        }
        finally{
            System.out.println("error on load");
        }
    }
    void SaveAndLoad() throws IOException{

        SaveAndLoad s= new SaveAndLoad();
        Folder f = s.LoadLibrary();
        System.out.println("Books count = " + f.GetBooks().size());
        System.out.println("Pages in folder count = " + f.GetPages().size());
        
    }
    void FolderTest() {
        Folder f = new Folder();
        Book book = new Book();
        Page page= new Page("the opening of this book");
        book.AddPage(page);;
        Tag tag = new Tag(Color.BLACK, "Books i have marked with a black tag");
        // these are excpected values, and will be not cause trubble
        f.SearchBooksString("the opening of this book");
        f.SearchBooksTag(tag); // searching the folder for a book with a black tag
        f.SearchPagesBID(0); // the first book to be instantiated has this book id, this will return that
                             // book
        f.SearchPagesString("");
        f.SearchPagesTag(null);
        
        // testing some edgecases
        f.SearchBooksString(null); // searching for nullstring in book in folder
        f.SearchBooksTag(null); // searcing by null tag
        f.SearchPagesBID(-2); // searching by negative book id, should never be able to return a value
        f.SearchPagesString(null); // searching for nullstring in pages in books in folder
        f.SearchPagesTag(null);
    }

    void PageTest() {
        Page p = new Page(null);
        // these are excpected values, and will be not cause trubble
        p.SetColor(Color.BLACK, Color.WHITE);
        p.SetPage("");
        p.SetTitle("ValidTitle");

        // testing some edgecases
        p.SetPage("i am malicious and will try to cause trouble .txt");
        /*
        try{
            p.SetColor(Color.ALICEBLUE, Color.rgb(256, 256, 256));
            p.SetTitle("i am malicious and will try to cause trouble .txt");
        }
        finally{
            System.out.println("these are invalid arguments");
        }
         */
        // p.page=""; //just testing to se if the page is protected, it is, so this
        // needs to be commented out for the compier to compile
        /*
         * p.SetTitle(123);
         * p.SetColor(123, 123);
         * p.SetPage(123);
         * p.RegisterTag(123);
         */
        //these are invalid arguments
    }

    void BookTest() {
        Book b = new Book();
        // these are excpected values, and will be not cause trubble
        b.SetColor(Color.BLACK, Color.BLUE);
        b.SearchPagesString("word");
        b.SetTitle("Winnie The Poo");

        // testing some edgecases
            b.SearchPagesString(null); // searching for null string
        try{
            b.SetColor(Color.ALICEBLUE, Color.rgb(255, 255, 255));
            b.SetTitle("created by the test");
        }
        finally{
            System.out.println("rgb colors only allow 0-255, .txt not allowed");
        }
        // b.pagearray=""; //just testing to se if the pagearray is protected, it is, so
        // this needs to be commented out for the compier to compile
    }
}
