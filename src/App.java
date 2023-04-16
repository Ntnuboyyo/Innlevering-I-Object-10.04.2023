import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.*;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class App extends Application {
    public static Pane bookfocus = null;
    public static Pane pagefocus = null;
    public static Pane focus = null;
    public static String focustext = "";
    public static Book innerbookfocus = null;
    public static Page innerpagefocus = null;
    public static Colorable innerfocus = null;
    // public static String innerfocus = null;
    public Folder MainFolder = new Folder();;
    public static Color[] randomcolorarray;
    public HBox root = new HBox(200);
    public VBox Books = new VBox(10);
    public VBox Pages = new VBox(10);
    public TextArea pagetext = new TextArea();

    public static void main(String[] args) throws Exception {
        System.out.println("Application starting up!");
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI.fxml"));
        HBox root = new HBox(200);
        VBox Books = new VBox(10);
        VBox Pages = new VBox(10);
        VBox rightsidepannel = new VBox();
        Button textsavebutton = AddSaveButtonToTextArea(Books, Pages, rightsidepannel, pagetext);
        TextArea pagetext = new TextArea();

        rightsidepannel.getChildren().addAll(pagetext, textsavebutton);
        // VBox Pages = new VBox();
        root.getChildren().addAll(Books, Pages, rightsidepannel);
        try {
            SaveAndLoad s = new SaveAndLoad();
            Folder f = s.LoadLibrary();
            MainFolder = f;
            try {
                if (!MainFolder.GetBooks().isEmpty()) {
                    Books.getChildren().addAll(DisplayBooks(f, Books, Pages, pagetext));
                    Book selectedbook = MainFolder.GetBooks().get(0);
                    SwapFocus((Colorable) selectedbook, bookfocus, Books, Pages, pagetext);
                } else {
                    Books.getChildren().add(0, NewBookButton(Books, Pages, pagetext));
                    Pages.getChildren().add(0, NewPageButton(Books, Pages, pagetext));

                }
            } catch (java.lang.NullPointerException error) {
                System.out.println("unable to focus on file load");
            }
        } finally {
            System.out.println("unable to load files");
        }
        if (Books.getChildren().isEmpty()) {
            Pages.setDisable(true);
            pagetext.setEditable(false);
        }

        Scene scene = new Scene(root);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent testkey) {
                if (testkey.getCode() == KeyCode.T) {
                    // System.out.println("running tests" + testkey.getCode());

                    try {
                        Tester t = new Tester();
                        t.RunnAllTests();
                        t = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        // System.out.println("some of the tests failed");
                    }
                    testkey.consume();
                }
            }
        });
        stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                try {
                    SaveAndLoad snl = new SaveAndLoad();
                    snl.SaveApplicationStates(MainFolder, innerfocus, innerbookfocus, innerpagefocus, pagetext);
                    Platform.exit();
                    System.exit(0);
                } finally {
                    System.out.println("Unable to save on exit");
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    void RightClickColorable() {

    }

    void HideTextField() {
        pagetext.setEditable(false);
        pagetext.opacityProperty();
    }

    void ShowTextField() {
        pagetext.setEditable(true);
    }

    Button AddSaveButtonToTextArea(VBox Books, VBox Pages, VBox rightsidepannel, TextArea pagetext) {
        Button savebutton = new Button("Save text");
        savebutton.setOnAction(save -> {
            if (innerpagefocus != null) {
                innerpagefocus.SetPage(pagetext.getText());
                System.out.println("innerpagefocus= " + innerpagefocus.GetTitle());
                System.out.println("page = " + innerpagefocus.GetPage());
                System.out.println("getText = " + pagetext.getText());
            } else {
                System.out.println("Innerpagefocus is nul");
            }
        });
        return savebutton;
    }

    void AddEditables(Colorable colorable, StackPane pane, VBox Books, VBox Pages, TextArea pagetext) {
        VBox colorableholder = new VBox();

        TextField tf = new TextField();
        pane.setOnMouseClicked(
                select -> {
                    if (select.getButton() == MouseButton.PRIMARY) {
                        SelectColorable(colorable, pane, Books, Pages, pagetext);
                    } else {
                        AddEditables(colorable, pane, Books, Pages, pagetext);
                    }

                });

        Button savebutton = new Button("Save " + colorable.getClass().toString());
        if (colorable instanceof Book) {
            savebutton.setOnAction(save -> {
                Node finbook = colorableholder.getChildren().get(0);
                colorableholder.getChildren().clear();
                Books.getChildren().addAll(finbook);
                colorable.SetTitle(tf.getText());
                Text label = ((Text) pane.getChildren().get(1));
                label.setText(tf.getText());
                label.toFront();
                Folder.AddBook((Book) colorable); // the book is now in the scene, add it to the folder, so it can be
                                                  // iterated over
                SwapFocus(colorable, pane, Books, Pages, pagetext);
                // Pages.setDisable(true);
                pagetext.setEditable(false);
            });
        }
        if (colorable instanceof Page) {
            savebutton.setOnAction(save -> {
                Node finpage = colorableholder.getChildren().get(0);
                colorableholder.getChildren().clear();
                Pages.getChildren().addAll(finpage);
                colorable.SetTitle(tf.getText());
                Text label = ((Text) pane.getChildren().get(1));
                label.setText(tf.getText());
                label.toFront();
                pagetext.setEditable(true);
                innerbookfocus.AddPage((Page) colorable); // the page is now in the scene, add it to the book, so it can
                                                          // be
                // iterated over
                SwapFocus(colorable, pane, Books, Pages, pagetext);
                // textfocus = pagetext;
                System.out.println("page has been added");
            });
        }
        if (colorable instanceof Tag) {
            savebutton.setOnAction(save -> {
                Node fintag = colorableholder.getChildren().get(0);
                colorableholder.getChildren().clear();
                Books.getChildren().addAll(fintag);
                colorable.SetTitle(tf.getText());
                Text label = ((Text) pane.getChildren().get(1));
                label.setText(tf.getText());
                label.toFront();
                SwapFocus(colorable, pane, Books, Pages, pagetext);
            });
        }

        ColorPicker color1 = new ColorPicker();
        color1.setOnAction(picked -> {
            Color pickedcolor = color1.getValue();
            colorable.SetColor(pickedcolor, null);
            Text t = (Text) pane.getChildren().get(1);
            t.setFill(pickedcolor);
            Rectangle r = (Rectangle) pane.getChildren().get(0);
            r.setFill(pickedcolor);
        });

        ColorPicker color2 = new ColorPicker();
        color2.setOnAction(picked -> {
            Color pickedcolor2 = color2.getValue();
            colorable.SetColor(null, pickedcolor2);
            Rectangle r = (Rectangle) pane.getChildren().get(0);
            r.setStroke(pickedcolor2);
        });
        colorableholder.getChildren().addAll(pane, color1, color2, tf, savebutton);
        if (colorable instanceof Book) {
            Books.getChildren().remove(pane);
            Books.getChildren().add(colorableholder);
        } else if (colorable instanceof Page) {
            Pages.getChildren().remove(pane);
            Pages.getChildren().add(colorableholder);
        }
    }

    ArrayList<StackPane> DisplayBooks(Folder MainFolder, VBox Books, VBox Pages, TextArea pagetext) {
        Books.getChildren().clear();
        StackPane newbookButton = NewBookButton(Books, Pages, pagetext);
        ArrayList<StackPane> bookarray = new ArrayList<>();
        bookarray.add(newbookButton);
        for (Book book : MainFolder.GetBooks()) {
            // Books.getChildren().add(DisplayBook(book, Books, Pages, pagetext));
            bookarray.add(DisplayColorable(book, Books, Pages, pagetext));
        }
        return bookarray;
    }

    /*
     * StackPane DisplayBook(Book book, VBox Books, VBox Pages, TextArea pagetext) {
     * //Pages.getChildren().clear();
     * StackPane stackPane = new StackPane();
     * Text text = new Text(book.GetTitle());
     * 
     * text.setFill(Color.BLACK);
     * text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, FontPosture.REGULAR,
     * 22));
     * text.setX(120);
     * text.setY(45);
     * 
     * Rectangle back = new Rectangle(5, 5, 280, 90);
     * back.setFill(book.GetColor()[0]);
     * back.setStrokeWidth(5);
     * back.setStroke(book.GetColor()[1]);
     * stackPane.getChildren().addAll(back, text);
     * text.toFront();
     * stackPane.setOnMouseClicked(select -> {
     * if (select.getButton() == MouseButton.PRIMARY) {
     * SelectColorable(stackPane, Books, Pages, pagetext);
     * } else {
     * AddEditables(book, stackPane, Books, Pages, pagetext);
     * }
     * 
     * });
     * //Books.getChildren().add(stackPane);
     * return stackPane;
     * }
     */
    /*
     * void DisplayColorableChildren(book, VBox Books, VBox Pages, TextArea
     * pagetext){
     * 
     * }
     */
    StackPane DisplayColorable(Colorable colorable, VBox Books, VBox Pages, TextArea pagetext) {

        StackPane stackPane = new StackPane();
        Text text = new Text(colorable.GetTitle());

        text.setFill(Color.BLACK);
        text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, FontPosture.REGULAR, 27));
        text.setX(120);
        text.setY(45);

        Rectangle back = new Rectangle(5, 5, 280, 90);
        back.setFill(colorable.GetColor()[0]);
        back.setStrokeWidth(5);
        back.setStroke(colorable.GetColor()[1]);
        stackPane.getChildren().addAll(back, text);

        stackPane.setOnMouseClicked(select -> {
            if (select.getButton() == MouseButton.PRIMARY) {
                SelectColorable(colorable, stackPane, Books, Pages, pagetext);
            } else {
                AddEditables(colorable, stackPane, Books, Pages, pagetext);
            }

        });
        text.toFront();
        return stackPane;
    }

    /*
     * StackPane DisplayPage(Page page, VBox Books, VBox Pages, TextArea pagetext) {
     * Pages.getChildren().clear();
     * StackPane stackPane = new StackPane();
     * Text text = new Text(page.GetTitle());
     * 
     * text.setFill(Color.BLACK);
     * text.setFont(Font.font("TimesNewRoman", FontWeight.BOLD, FontPosture.REGULAR,
     * 22));
     * text.setX(120);
     * text.setY(45);
     * 
     * Rectangle back = new Rectangle(5, 5, 280, 90);
     * back.setFill(page.GetColor()[0]);
     * back.setStrokeWidth(5);
     * back.setStroke(page.GetColor()[1]);
     * stackPane.getChildren().addAll(back, text);
     * text.toFront();
     * stackPane.setOnMouseClicked(select -> {
     * if (select.getButton() == MouseButton.PRIMARY) {
     * SelectColorable(stackPane, Books, Pages, pagetext);
     * } else {
     * AddEditables(page, stackPane, Books, Pages, pagetext);
     * }
     * 
     * });
     * //Books.getChildren().add(stackPane);
     * return stackPane;
     * }
     */
    ArrayList<StackPane> DisplayPages(Book book, VBox Books, VBox Pages, TextArea pagetext) {
        Pages.getChildren().clear(); // remove old displayed pages.SearchPagesBID(bid);

        ArrayList<StackPane> pagearray = new ArrayList<StackPane>();
        StackPane newpagebutton = NewPageButton(Books, Pages, pagetext);
        Pages.getChildren().add(newpagebutton);
        for (Page pageinbook : book.GetPages()) {
            StackPane page = DisplayColorable(pageinbook, Books, Pages, pagetext);
            pagearray.add(page);
        }
        return pagearray;
        // System.out.println("Display pages called!");
    }

    StackPane NewBookButton(VBox Books, VBox Pages, TextArea pagetext) {
        // System.out.println("NewBookButton called!");
        Button NewBookButton = new Button("Make New Book");
        NewBookButton.setOnMouseClicked(
                e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Book innerbook = new Book();
                        Folder.AddBook(innerbook);
                        AddEditables(innerbook, DisplayColorable(innerbook, Books, Pages, pagetext), Books, Pages,
                                pagetext);
                    }
                });
        StackPane pane = new StackPane();
        pane.getChildren().add(NewBookButton);
        return pane;
    }

    StackPane NewPageButton(VBox Books, VBox Pages, TextArea pagetext) {
        // System.out.println("NewPageButton called!");
        Button NewPageButton = new Button("Make New Page");
        NewPageButton.setOnMouseClicked(
                e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Page innerpage = new Page("");
                        innerbookfocus.AddPage(innerpage);
                        AddEditables(innerpage, DisplayColorable(innerpage, Books, Pages, pagetext), Books, Pages,
                                pagetext);
                    }
                });
        StackPane pane = new StackPane();
        pane.getChildren().add(NewPageButton);
        return pane;
    }
    /*
     * Button NewPageButton(VBox Books, VBox Pages, TextArea pagetext) {
     * System.out.println("NewPageButton called!");
     * Button NewPageButton = new Button("Make New Page");
     * NewPageButton.setOnMouseClicked(
     * e -> {
     * if (e.getButton() == MouseButton.PRIMARY) {
     * Page innerpage = new Page("");
     * innerpage.SetTitle("");
     * AddEditables(innerpage, DisplayPage(innerpage, Books, Pages, pagetext),
     * Books, Pages,
     * pagetext);
     * 
     * VBox pageholder = new VBox();
     * Rectangle page = new Rectangle(5, 5, 380, 55);
     * page.setStrokeWidth(5);
     * Page innerpage = new Page("");
     * Button savepage = new Button("Save Page");
     * 
     * savepage.setOnAction(save -> {
     * Node finpage = pageholder.getChildren().get(0);
     * pageholder.getChildren().clear();
     * Pages.getChildren().addAll(finpage);
     * 
     * });
     * ColorPicker pagecolor = new ColorPicker();
     * pagecolor.setOnAction(picked -> {
     * Color pickedcolor = pagecolor.getValue();
     * innerpage.SetColor(pickedcolor, null);
     * page.setFill(pickedcolor);
     * });
     * ColorPicker bookstroke = new ColorPicker();
     * bookstroke.setOnAction(picked -> {
     * Color pickedcolor2 = bookstroke.getValue();
     * innerpage.SetColor(null, pickedcolor2);
     * page.setStroke(pickedcolor2);
     * });
     * pageholder.getChildren().addAll(page, pagecolor, bookstroke, savepage);
     * Pages.getChildren().add(pageholder);
     * 
     * }
     * });
     * return NewPageButton;
     * }
     */

    void SelectColorable(Colorable colorable, Pane pane, VBox Books, VBox Pages, TextArea pagetext) {
        if (Books.getChildren().contains(pane)) {
            Pages.getChildren().addAll(DisplayPages((Book) colorable, Books, Pages, pagetext));
            System.out.println("----------------------------------------------------");
        }
        if (Pages.getChildren().contains(pane)) {
            TextArea newpagetext = new TextArea(innerpagefocus.GetPage());
            pagetext = newpagetext;
            System.out.println("llllllllllllllllllllllllll");
        }
    }

    void SwapFocus(Colorable colorable, Pane pane, VBox Books, VBox Pages, TextArea pagetext) {
        // another thing has been clicked, we swap the focus, saving the textareas in
        // case they would be deleted by this action
        // innerpagefocus.SetPage(pagetext.toString());
        /*
         * if (pagefocus != null) {
         * String currentpagestring = pagetext.getText();
         * ((Page) innerfocus).SetPage(currentpagestring);
         * System.out.println("page has info and is added to book");
         * }
         */
        if (colorable instanceof Book) {
            innerbookfocus = (Book) colorable;
        }
        if (colorable instanceof Page) {
            innerpagefocus = (Page) colorable;
        }
        innerfocus = colorable;
        focus = pane;
        focustext = pagetext.getText();
        if (innerbookfocus != null) {
            if (innerpagefocus != null) {
                innerbookfocus.GetPages().remove((Page) innerpagefocus);
                innerpagefocus.SetPage(focustext);
                innerbookfocus.GetPages().add(innerpagefocus);
            }
        }
        if (innerfocus != null) {
            SelectColorable(colorable, pane, Books, Pages, pagetext);
        }
    }
}
