import javafx.scene.paint.Color;

public interface Colorable {
    public void SetColor(Color a,Color b);
    public Color[] GetColor();
    public void RegisterTag(Tag tag);
    public String GetTitle();
    public void SetTitle(String text);
}
