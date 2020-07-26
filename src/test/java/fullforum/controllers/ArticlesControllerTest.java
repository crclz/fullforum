package crclz.fullforum.controllers;

import crclz.fullforum.dependency.FakeAuth;
import crclz.fullforum.BaseTest;
import crclz.fullforum.data.repos.ArticleRepository;
import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.dto.in.CreateArticleModel;
import crclz.fullforum.errhand.UnauthorizedException;
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
}