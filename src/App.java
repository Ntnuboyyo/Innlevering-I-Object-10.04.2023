import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.*;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        System.out.println("Application starting up!");
        Application.launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI.fxml"));
        HBox root = new HBox(200);
        VBox Folder = new VBox();
        VBox Books = new VBox();
        TextArea pagetext = new TextArea();
        // VBox Pages = new VBox();
        root.getChildren().addAll(Folder, Books, pagetext);

        
        Folder.getChildren().add(0, NewBookButton(Folder, Books,pagetext));

        Books.getChildren().add(0, NewPageButton(Folder, Books,pagetext));

        Scene scene = new Scene(root);
        Button pagebutton1 = new Button();
        pagebutton1.setOnMouseClicked(
                e -> {
                    root.getChildrenUnmodifiable().add(0, pagebutton1);
                    // NewBook.getParent();
                });
        stage.setScene(scene);
        stage.show();
    }
    void RightClickBook(){

    }
    Button NewBookButton(VBox Folder,VBox Books,TextArea pagetext){
        Button NewBookButton = new Button("Make New Book");
        NewBookButton.setOnMouseClicked(
                e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        VBox pageholder = new VBox();
                        Rectangle page = new Rectangle(5, 5, 280, 95);
                        page.setStrokeWidth(5);
                        Page innerpage = new Page("");
                        Button savebook = new Button("Save Book");
                        
                        savebook.setOnAction(save -> {
                            Node finbook = pageholder.getChildren().get(0);
                            pageholder.getChildren().clear();
                            Books.getChildren().addAll(finbook);

                        });
                        ColorPicker bookcolor = new ColorPicker();
                        bookcolor.setOnAction(picked -> {
                            Color pickedcolor = bookcolor.getValue();
                            innerpage.SetColor(pickedcolor);
                            page.setFill(pickedcolor);
                        });
                        ColorPicker bookstroke = new ColorPicker();
                        bookstroke.setOnAction(picked -> {
                            Color pickedcolor2 = bookstroke.getValue();
                            page.setStroke(pickedcolor2);
                        });
                        pageholder.getChildren().addAll(page,bookcolor,bookstroke,savebook);
                        Folder.getChildren().add(pageholder);

                    }
                });
                return NewBookButton;
    }
    Button NewPageButton(VBox Folder,VBox Books,TextArea pagetext){
        Button NewPageButton = new Button("Make New Page");
        NewPageButton.setOnMouseClicked(
                e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        VBox bookholder = new VBox();
                        Rectangle book = new Rectangle(5, 5, 380, 55);
                        book.setStrokeWidth(5);
                        Book innerbook = new Book();
                        Button savebook = new Button("Save Book");
                        
                        savebook.setOnAction(save -> {
                            Node finbook = bookholder.getChildren().get(0);
                            bookholder.getChildren().clear();
                            Books.getChildren().addAll(finbook);

                        });
                        ColorPicker bookcolor = new ColorPicker();
                        bookcolor.setOnAction(picked -> {
                            Color pickedcolor = bookcolor.getValue();
                            innerbook.SetColor(pickedcolor);
                            book.setFill(pickedcolor);
                        });
                        ColorPicker bookstroke = new ColorPicker();
                        bookstroke.setOnAction(picked -> {
                            Color pickedcolor2 = bookstroke.getValue();
                            book.setStroke(pickedcolor2);
                        });
                        bookholder.getChildren().addAll(book,bookcolor,bookstroke,savebook);
                        Books.getChildren().add(bookholder);

                    }
                });
                return NewPageButton;
    }
    void SelectBook(){
        
    }
}
