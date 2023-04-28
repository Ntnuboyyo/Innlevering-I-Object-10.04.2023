import javafx.scene.shape.Rectangle;

public class BookInScene {
    //fx:id is a better a pproach than this
    Rectangle bookvisual;
    Book book;
    BookInScene(Rectangle bookvisual,Book book){
        this.bookvisual=bookvisual;
        this.book=book;
    }
}
