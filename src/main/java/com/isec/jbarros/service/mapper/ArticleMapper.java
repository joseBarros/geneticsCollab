package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.Article;
import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.dto.NamedEntityDTO;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Article} and its DTO {@link ArticleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<ArticleDTO, Article> {
    @Mapping(target = "namedEntities", source = "namedEntities", qualifiedByName = "namedEntityTextSet")
    @Mapping(target = "nlpModel", source = "nlpModel", qualifiedByName = "nLPModelId")
    ArticleDTO toDto(Article s);

    //@Mapping(target = "removeEntities", ignore = true)
    //Article toEntity(ArticleDTO articleDTO);

    @Named("namedEntityText")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "startChar", source = "startChar")
    @Mapping(target = "endChar", source = "endChar")
    @Mapping(target = "tag", source = "tag")
    NamedEntityDTO toDtoNamedEntityText(NamedEntity namedEntity);

    @Named("namedEntityTextSet")
    default Set<NamedEntityDTO> toDtoNamedEntityTextSet(Set<NamedEntity> namedEntity) {
        return namedEntity.stream().map(this::toDtoNamedEntityText).sorted(Comparator.comparingInt(NamedEntityDTO::getStartChar)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Named("nLPModelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tags", source = "tags")
    NLPModelDTO toDtoNLPModelId(NLPModel nLPModel);

    default NLPModelDTO nLPModelToNLPModelDTO(NLPModel nLPModel) {
        if ( nLPModel == null ) {
            return null;
        }

        NLPModelDTO nLPModelDTO = new NLPModelDTO();

        nLPModelDTO.setId( nLPModel.getId() );
        nLPModelDTO.setName( nLPModel.getName() );
        nLPModelDTO.setFramework( nLPModel.getFramework() );
        nLPModelDTO.setPath( nLPModel.getPath() );
        nLPModelDTO.setNotes( nLPModel.getNotes() );
        //nLPModelDTO.setTags( tagSetToTagDTOSet( nLPModel.getTags() ) );

        return nLPModelDTO;
    }
}
