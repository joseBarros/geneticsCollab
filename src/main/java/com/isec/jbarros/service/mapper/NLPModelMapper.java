package com.isec.jbarros.service.mapper;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.dto.TagDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NLPModel} and its DTO {@link NLPModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface NLPModelMapper extends EntityMapper<NLPModelDTO, NLPModel> {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagLabelSet")
    NLPModelDTO toDto(NLPModel s);

    @Mapping(target = "removeTags", ignore = true)
    NLPModel toEntity(NLPModelDTO nLPModelDTO);

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
