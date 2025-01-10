package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.Article;
import com.isec.jbarros.repository.ArticleRepository;
import com.isec.jbarros.repository.NamedEntityRepository;
import com.isec.jbarros.repository.TagRepository;
import com.isec.jbarros.service.ArticleService;
import com.isec.jbarros.service.UserService;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.service.mapper.ArticleMapper;

import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.isec.jbarros.domain.Article}.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final NamedEntityRepository namedEntityRepository;

    private final TagRepository tagRepository;

    private final ArticleMapper articleMapper;

    private final UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, NamedEntityRepository namedEntityRepository, TagRepository tagRepository, ArticleMapper articleMapper, UserService userService) {
        this.articleRepository = articleRepository;
        this.namedEntityRepository = namedEntityRepository;
        this.tagRepository = tagRepository;
        this.articleMapper = articleMapper;
        this.userService = userService;
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article.setUser(userService.getUserWithAuthorities().orElseThrow());
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    public ArticleDTO update(ArticleDTO articleDTO) {
        log.debug("Request to update Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);
        article.setUser(userService.getUserWithAuthorities().orElseThrow());
        article = articleRepository.save(article);
        return articleMapper.toDto(article);
    }

    @Override
    public Optional<ArticleDTO> partialUpdate(ArticleDTO articleDTO) {
        log.debug("Request to partially update Article : {}", articleDTO);

        return articleRepository
            .findById(articleDTO.getId())
            .map(existingArticle -> {
                articleMapper.partialUpdate(existingArticle, articleDTO);

                return existingArticle;
            })
            .map(articleRepository::save)
            .map(articleMapper::toDto);
    }

    @Override
    public Page<ArticleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");

        String userId = userService.getUserWithAuthorities().orElseThrow().getId();
        //get all articles from all users in case is admin
        if(userService.getUserWithAuthorities().orElseThrow().getAuthorities().stream().filter(authority -> authority.getName().equals("ROLE_ADMIN")).findFirst().orElse(null) != null){
            return articleRepository.findAll(pageable).map(articleMapper::toDto);
        }
        //for normal users, gets user own articles
        return articleRepository.findByUserId(userId, pageable).map(articleMapper::toDto);
    }

    @Override
    public Optional<ArticleDTO> findOne(String id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id).map(articleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Delete Named entities for Article : {}", id);
        Objects.requireNonNull(articleRepository.findById(id).orElse(null)).getNamedEntities().forEach(namedEntity -> {namedEntityRepository.deleteById(namedEntity.getId());});
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
