package fpt.captonse.dfn.repository;

import fpt.captonse.dfn.domain.Article;

import fpt.captonse.dfn.domain.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data  repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByEmail(String email, Pageable pageable);

    Page<Article> findArticlesByLabelIdAndEmail(Label label,String email, Pageable pageable);

    @Query(value = "SELECT * FROM db.articles as c where c.label_id_id=?1", nativeQuery = true)
    Page<Article> getNumber(String label,Pageable pageable);
}
