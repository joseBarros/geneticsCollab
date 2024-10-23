package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.Article;
import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.service.dto.ArticleDTO;
import com.isec.jbarros.service.dto.NLPModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NLPModel} and its DTO {@link NLPModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface NLPModelMapper extends EntityMapper<NLPModelDTO, NLPModel> {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tags", source = "tags")
    NLPModelDTO toDto(NLPModel s);
}
