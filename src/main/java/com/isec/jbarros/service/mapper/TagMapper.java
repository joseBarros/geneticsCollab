package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.service.dto.TagDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {}
