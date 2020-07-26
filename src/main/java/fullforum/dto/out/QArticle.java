package fullforum.dto.out;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import fullforum.data.models.Article;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class QArticle {
    public long id;
    public String title;
    public String text;
    public long userId;

    public Quser user;

    public static QArticle convert(Article p, Quser user, ModelMapper mapper) {
        var qArticle = mapper.map(p, QArticle.class);
        qArticle.user = user;
        return qArticle;
    }
}
