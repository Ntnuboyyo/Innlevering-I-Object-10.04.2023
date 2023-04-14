import java.util.ArrayList;
import java.util.Collection;

public interface Taggable {
    Collection<Tag> tags = new ArrayList<Tag>();
    void RegisterTag(Tag tag);
}
