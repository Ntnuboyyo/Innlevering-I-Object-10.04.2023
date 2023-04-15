import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Control {

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
        button.setOnMouseClicked(
            e -> {
                NewBook.getParent().getChildrenUnmodifiable().add(0, button);
                //NewBook.getParent();
            });
            */
    }

}
