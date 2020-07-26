package fullforum.controllers;

import fullforum.data.models.Article;
import fullforum.dependency.FakeAuth;
import fullforum.BaseTest;
import fullforum.data.repos.ArticleRepository;
import fullforum.data.repos.UserRepository;
import fullforum.dto.in.CreateArticleModel;
import fullforum.dto.in.PatchArticleModel;
import fullforum.errhand.ForbidException;
import fullforum.errhand.NotFoundException;
import fullforum.errhand.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

public class ArticlesControllerTest extends BaseTest {
    @Autowired
    ArticlesController articlesController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    FakeAuth auth;

    @Autowired
    MockMvc mockMvc;


    //region createArticle test

    @Test
    void createArticle_throw_unauthorized_when_not_log_in() {
        var model = new CreateArticleModel("asd", "asdaa");
        assertThrows(UnauthorizedException.class, () -> articlesController.createArticle(model));
    }

    @Test
    void createArticle_succeed_and_insert_to_db_when_all_ok() {
        // Arrange
        auth.setRealUserId(1);
        var model = new CreateArticleModel("asd", "asdaa");

        // Act
        var idDto = articlesController.createArticle(model);
        var articleId = idDto.getLongId();

        // Assert
        var article = articleRepository.getOne(articleId);

        assertThat(article.getId()).isEqualTo(articleId);
        assertThat(article.getTitle()).isEqualTo(model.title);
        assertThat(article.getText()).isEqualTo(model.text);
    }

    //endregion


    //region removeArticle test

    @Test
    void removeArticle_throw_unauthorized_when_not_login() {
        assertThrows(UnauthorizedException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArticle_throw_notfound_when_article_not_exist() {
        // Arrange
        auth.setRealUserId(1);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArticle_throw_forbid_when_article_not_belong_to_current_user() {
        // Arrange
        auth.setRealUserId(1);
        var article = new Article(1, "asd", "aaaaa", 2);
        articleRepository.save(article);

        // Act & Assert
        assertThrows(ForbidException.class, () -> articlesController.removeArticle(1));
    }

    @Test
    void removeArticle_succeed_and_change_db_when_all_ok() {
        // Arrange
        auth.setRealUserId(1);
        var article = new Article(1, "asd", "aaaaa", 1);
        articleRepository.save(article);

        // Act
        articlesController.removeArticle(1);

        // Assert
        var articleInDb = articleRepository.findById(1L).orElse(null);
        assertThat(articleInDb).isNull();
    }

    //endregion


    //region patchArticle test

    @Test
    void patchArticle_throw_unauthorized_when_not_login() {
        var model = new PatchArticleModel("aaa", "bbbb");
        assertThrows(UnauthorizedException.class, () -> articlesController.patchArticle(1, model));
    }

    @Test
    void patchArticle_throw_not_found_when_article_not_exist() {
        auth.setRealUserId(1);

        var model = new PatchArticleModel("aaa", "bbbb");
        assertThrows(NotFoundException.class, () -> articlesController.patchArticle(1, model));
    }

    @Test
    void patchArticle_throw_forbid_when_article_not_belong_to_current_user() {
        // Arrange
        auth.setRealUserId(1);
        var article = new Article(1, "aaa", "ccccc", 2);
        articleRepository.save(article);

        // Act & Assert
        var model = new PatchArticleModel("aaa", "bbbb");
        assertThrows(ForbidException.class, () -> articlesController.patchArticle(1, model));

    }

    @Test
    void patchArticle_succeed_when_all_ok_and_update_db() {
        // Arrange
        auth.setRealUserId(1);
        var article = new Article(1, "aaa", "ccccc", 1);
        articleRepository.save(article);

        // Act
        var model = new PatchArticleModel("1111", "2222");
        articlesController.patchArticle(1, model);

        // Assert
        var articleInDb = articleRepository.getOne(article.getId());
        assertThat(articleInDb.getTitle()).isEqualTo(model.title);
        assertThat(articleInDb.getText()).isEqualTo(model.text);
    }

    //endregion
}