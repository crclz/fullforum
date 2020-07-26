package fullforum.dto.out;

import fullforum.data.models.Article;

public class QArticle {
    public String id;
    public String title;
    public String text;
    public String userId;

    public Quser user;

    public QArticle(Long id, String title, String text, Long userId, Quser user) {
        this.id = id.toString();
        this.title = title;
        this.text = text;
        this.userId = userId.toString();

        this.user = user;
    }

    public static QArticle convert(Article p, Quser user) {
        return p == null ? null :
                new QArticle(p.getId(), p.getTitle(), p.getText(), p.getUserId(), user);
    }
}
