package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    @Mapping(target = "nlpModel", source = "nlpModel")
    TagDTO toDto(Tag s);
}
