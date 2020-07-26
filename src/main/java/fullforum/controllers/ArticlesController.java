package fullforum.controllers;

import fullforum.data.models.Article;
import fullforum.data.repos.ArticleRepository;
import fullforum.data.repos.UserRepository;
import fullforum.dto.in.CreateArticleModel;
import fullforum.dto.out.IdDto;
import fullforum.errhand.UnauthorizedException;
import fullforum.services.IAuth;
import fullforum.services.Snowflake;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("articles")
public class ArticlesController {
    @Autowired
    Snowflake snowflake;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    IAuth auth;

    @PostMapping
    public IdDto createArticle(@RequestBody @Valid CreateArticleModel model) {
        if (!auth.isLoggedIn()) {
            throw new UnauthorizedException();
        }

        var article = new Article(snowflake.nextId(), model.title, model.text, auth.userId());
        articleRepository.save(article);

        return new IdDto(article.getId());
    }
}
