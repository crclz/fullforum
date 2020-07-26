package fullforum.dto.out;

import fullforum.data.models.Article;

public class QArticle {
    public String id;
    public String title;
    public String text;
    public String userId;

    public QArticle(Long id, String title, String text, Long userId) {
        this.id = id.toString();
        this.title = title;
        this.text = text;
        this.userId = userId.toString();
    }

    public static QArticle convert(Article p) {
        return p == null ? null : new QArticle(p.getId(), p.getTitle(), p.getText(), p.getUserId());
    }
}
