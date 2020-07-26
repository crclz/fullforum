package fullforum.controllers;

import fullforum.data.models.Article;
import fullforum.data.repos.ArticleRepository;
import fullforum.data.repos.UserRepository;
import fullforum.dto.in.CreateArticleModel;
import fullforum.dto.in.PatchArticleModel;
import fullforum.dto.out.IdDto;
import fullforum.dto.out.QArticle;
import fullforum.errhand.ForbidException;
import fullforum.errhand.NotFoundException;
import fullforum.errhand.UnauthorizedException;
import fullforum.services.IAuth;
import fullforum.services.Snowflake;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

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

    @DeleteMapping("{id}")
    public void removeArticle(@PathVariable long id) {
        if (!auth.isLoggedIn()) {
            throw new UnauthorizedException();
        }

        var article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            throw new NotFoundException();
        }
        if (article.getUserId() != auth.userId()) {
            throw new ForbidException();
        }

        // ok
        articleRepository.delete(article);
    }

    @PatchMapping("{id}")
    public void patchArticle(@PathVariable long id, PatchArticleModel model) {
        if (!auth.isLoggedIn()) {
            throw new UnauthorizedException();
        }

        var article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            throw new NotFoundException();
        }
        if (article.getUserId() != auth.userId()) {
            throw new ForbidException();
        }

        // ok
        if (model.title != null) {
            article.setTitle(model.title);
        }
        if (model.text != null) {
            article.setText(model.text);
        }

        articleRepository.save(article);
    }

    @GetMapping("{id}")
    public QArticle getArticleById(@PathVariable long id) {
        var article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }

        return QArticle.convert(article);
    }
}
