package crclz.fullforum.controllers;

import crclz.fullforum.data.models.Article;
import crclz.fullforum.data.repos.ArticleRepository;
import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.dto.in.CreateArticleModel;
import crclz.fullforum.dto.out.IdDto;
import crclz.fullforum.errhand.UnauthorizedException;
import crclz.fullforum.services.IAuth;
import crclz.fullforum.services.Snowflake;
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

        var article=new Article()

        throw new NotYetImplementedException();
    }
}
