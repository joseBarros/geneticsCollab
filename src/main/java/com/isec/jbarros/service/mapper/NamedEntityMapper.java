package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.Article;
import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.service.dto.NamedEntityDTO;
import com.isec.jbarros.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NamedEntity} and its DTO {@link NamedEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface NamedEntityMapper extends EntityMapper<NamedEntityDTO, NamedEntity> {
    @Mapping(target = "article", source = "article", qualifiedByName = "articleId")
    @Mapping(target = "tag", source = "tag", qualifiedByName = "tagId")
    NamedEntityDTO toDto(NamedEntity s);

    @Named("articleId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ArticleDTO toDtoArticleId(Article article);

    @Named("tagId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    TagDTO toDtoTagId(Tag tag);
}
