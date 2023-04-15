import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.paint.Color;

public interface Colorable {
    void SetColor(Color c);
    Color GetColor();
    void RegisterTag(Tag tag);
}
