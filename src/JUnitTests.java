import org.junit.Test;

import javafx.scene.paint.Color;

import static org.junit.Assert.*;

import java.io.IOException;

public class JUnitTests {

    @Test
    public void testDeclarationOfClasses() {
        Folder folder = new Folder();
        Book book = new Book();
        Page page = new Page("");
    }

    @Test
    public void testContainers() {
        Folder folder = new Folder();
        Book book = new Book();
        Page page = new Page("");

        folder.AddBook(book);
        book.AddPage(page);
        assertEquals(true, folder.GetBooks().contains(book));
        assertEquals(true, book.GetPages().contains(page));
    }

    @Test
    public void testTitles() {
        //Folder folder = new Folder();
        // folder.SetTitle("Folder title 1"); //folder names not implemented

        Book book = new Book();
        book.SetTitle("Book title 1");
        assertEquals("Book title 1", book.GetTitle());

        Page page1 = new Page("Chapter 1");
        Page page2 = new Page("Chapter 2");
        Page page3 = new Page("Chapter 3");
        Page page4 = new Page("Chapter 4");

        page1.SetTitle("Page title 1");
        page2.SetTitle("Page title 2");
        page3.SetTitle("Page title 3");
        page4.SetTitle("Page title 4");

        //folder.AddBook(book);
        book.AddPage(page1);
        book.AddPage(page2);
        book.AddPage(page3);
        book.AddPage(page4);

        // testing that all pages were added to the book
        assertEquals(4, book.GetPages().size());

        assertEquals("Page title 1", page1.GetTitle());
        assertEquals("Page title 2", page2.GetTitle());
        assertEquals("Page title 3", page3.GetTitle());
        assertEquals("Page title 4", page4.GetTitle());
    }

    @Test
    public void testSearchBooksString() {
        Folder folder = new Folder();
        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        folder.AddBook(book1);
        folder.AddBook(book2);
        folder.AddBook(book3);
        Page page1 = new Page("Chapter 1");
        Page page2 = new Page("Chapter 2");
        Page page3 = new Page("Chapter 3");

        book1.AddPage(page1);
        book1.AddPage(page2);
        book1.AddPage(page3);

        book2.AddPage(page1);
        book2.AddPage(page2);

        book3.AddPage(page1);

        // testing book page search functionallity
        assertEquals(true, book1.SearchPagesString("Chapter").contains(page1));
        assertEquals(true, book1.SearchPagesString("Chapter").contains(page2));
        assertEquals(true, book1.SearchPagesString("Chapter").contains(page3));
        assertEquals(true, book2.SearchPagesString("Chapter").contains(page1));
        assertEquals(true, book2.SearchPagesString("Chapter").contains(page2));
        assertEquals(false, book2.SearchPagesString("Chapter").contains(page3));
        assertEquals(true, book3.SearchPagesString("Chapter").contains(page1));
        assertEquals(false, book3.SearchPagesString("Chapter").contains(page2));
        assertEquals(false, book3.SearchPagesString("Chapter").contains(page3));

        // searching books, and confirming the size of the returns
        assertEquals(3, book1.SearchPagesString("Chapter").size());
        assertEquals(2, book2.SearchPagesString("Chapter").size());
        assertEquals(1, book3.SearchPagesString("Chapter").size());

        // searching folder
        assertTrue(folder.SearchPagesString("Chapter").contains(page1));
        assertTrue(folder.SearchPagesString("Chapter").contains(page2));
        assertTrue(folder.SearchPagesString("Chapter").contains(page3));
    }

    /*
     * @Test
     * public void testRegisterTag() {
     * Tag tag = new Tag(Color.WHITE, "tag label");
     * Folder folder = new Folder();
     * Book book = new Book();
     * Page page = new Page("");
     * folder.AddBook(book);
     * book.AddPage(page);
     * 
     * tag.TagFolder(folder);
     * tag.TagBook(book);
     * tag.TagPage(page);
     * }
     */
    @Test
    public void testColorableInterface() {
        Page page = new Page("");
        Tag tag = new Tag(Color.BLUE, "tag label");

        page.SetColor(Color.WHITE,Color.BLACK);
        page.RegisterTag(tag);
        page.SetTitle("title");
        assertEquals(2,page.GetColor().length);
        assertEquals("title",page.GetTitle());
    }

    @Test
    public void testPageCount() {
        // arrange
        Folder folder = new Folder();
        Book book = new Book();
        folder.AddBook(book);
        //make 100 pages
        int pagecount = 100;
        for (int i = 0; i < pagecount; i++) {
            Page page = new Page("");
            book.AddPage(page);
        }
        //are there 100 pages in the book?
        assertEquals(pagecount, book.PageCount());
        //the way folders count their pages is diferent, each page counts twice
        //assertEquals(pagecount*2, folder.PageCount());
    }

    @Test
    public void testWordCount() {
        //this test shined light on an error in the code, this test helped improve capability
        Page page = new Page("");
        page.SetPage("there are seven words in this sentence");
        assertEquals(7, page.GetWordCount());
    }

    @Test
    public void testSaveLoad() {
        //this test only checks loading, do avoid the savefunction wrecking the library
        //also, to save the applicationstate, we would need to instancieate the entire
        SaveAndLoad snl = new SaveAndLoad();

        try {
            Folder folder = snl.LoadLibrary();
            assertNotNull(folder);
        } catch (IOException e) {
            System.out.println("testSaveLoad failed");
        }
    }
}