import java.util.Collection;

public interface PageContainer {
    Collection<Page> GetPages();
    Collection<Page> SearchPagesString(String search);
    Collection<Page> SearchPagesTag(Tag tag);
    int PageCount();
    int WordCount();
}
