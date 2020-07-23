package crclz.fullforum.data.models;

import crclz.fullforum.data.RootEntity;

import javax.persistence.Entity;

@Entity
public class Article extends RootEntity {

    private String title;
    private String text;
    private Long userId;

    protected Article() {
    }

    public Article(long id, String title, String text, Long userId) {
        super(id);
        setTitle(title);
        setText(text);
        setUserId(userId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
