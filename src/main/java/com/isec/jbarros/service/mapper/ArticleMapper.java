package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.Article;
import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.dto.NamedEntityDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "entities", source = "entities", qualifiedByName = "namedEntityTextSet")
    @Mapping(target = "model", source = "model", qualifiedByName = "nLPModelId")
    ArticleDTO toDto(Article s);

    @Mapping(target = "removeEntities", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    @Named("namedEntityText")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    NamedEntityDTO toDtoNamedEntityText(NamedEntity namedEntity);

    @Named("namedEntityTextSet")
    default Set<NamedEntityDTO> toDtoNamedEntityTextSet(Set<NamedEntity> namedEntity) {
        return namedEntity.stream().map(this::toDtoNamedEntityText).collect(Collectors.toSet());
    }

    @Named("nLPModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NLPModelDTO toDtoNLPModelId(NLPModel nLPModel);
}
