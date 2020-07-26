package fullforum.dto.out;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import fullforum.data.models.Article;
import lombok.Data;

@Data
public class QArticle {
    public long id;
    public String title;
    public String text;
    public long userId;

    public Quser user;

    public QArticle(long id, String title, String text, long userId, Quser user) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.userId = userId;

        this.user = user;
    }

    public static QArticle convert(Article p, Quser user) {
        return p == null ? null :
                new QArticle(p.getId(), p.getTitle(), p.getText(), p.getUserId(), user);
    }
}
