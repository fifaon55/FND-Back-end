package fpt.captonse.dfn.service;

import fpt.captonse.dfn.domain.Article;
import fpt.captonse.dfn.domain.Label;
import fpt.captonse.dfn.repository.ArticleRepository;
import fpt.captonse.dfn.service.dto.ArticleDTO;
import fpt.captonse.dfn.service.mapper.ArticleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    /**
     * Save a article.
     *
     * @param articleDTO the entity to save.
     * @return the persisted entity.
     */
    public ArticleDTO save(ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    /**
     * Get all the articles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAll(String email,Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAllByEmail(email,pageable)
            .map(articleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDTO> findAllByLabel(String num,String email,Pageable pageable) {
        Label label = new Label();
        if(num.equals("1")) {
            label.setId(Long.parseLong("1"));
        }else{
            label.setId(Long.parseLong("2"));
        }
        return articleRepository.findArticlesByLabelIdAndEmail(label,email,pageable)
            .map(articleMapper::toDto);
    }
    /**
     * Get one article by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArticleDTO> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id)
            .map(articleMapper::toDto);
    }

    /**
     * Delete the article by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
