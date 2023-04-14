import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PleaseProvideControllerClassName {

    @FXML
    private Button NewBook;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    void NewBook(ActionEvent event) {
        Button button = new Button();
        /*
        button.setOnMouseClicked(event ->  {
            Button pagebutton = new Button();
            //pagebutton.
            
         });
        */
        Book newbook = new Book();
    }

}
