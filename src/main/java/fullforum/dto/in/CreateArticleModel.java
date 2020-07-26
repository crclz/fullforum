package fullforum.dto.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateArticleModel {
    @NotNull
    @NotBlank
    public String title;

    @NotNull
    @NotBlank
    public String text;

    public CreateArticleModel() {
    }

    public CreateArticleModel(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
