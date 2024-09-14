package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.service.dto.NamedEntityDTO;
import com.isec.jbarros.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NamedEntity} and its DTO {@link NamedEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface NamedEntityMapper extends EntityMapper<NamedEntityDTO, NamedEntity> {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagLabelSet")
    NamedEntityDTO toDto(NamedEntity s);

    @Mapping(target = "removeTags", ignore = true)
    NamedEntity toEntity(NamedEntityDTO namedEntityDTO);

    @Named("tagLabel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "label", source = "label")
    TagDTO toDtoTagLabel(Tag tag);

    @Named("tagLabelSet")
    default Set<TagDTO> toDtoTagLabelSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagLabel).collect(Collectors.toSet());
    }
}
