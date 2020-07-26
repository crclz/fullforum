package crclz.fullforum.data.repos;

import crclz.fullforum.data.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
