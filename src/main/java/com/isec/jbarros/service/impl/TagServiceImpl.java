package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.Tag;
import com.isec.jbarros.repository.TagRepository;
import com.isec.jbarros.service.TagService;
import com.isec.jbarros.service.UserService;
import com.isec.jbarros.service.dto.TagDTO;
import com.isec.jbarros.service.mapper.TagMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.isec.jbarros.domain.Tag}.
 */
@Service
public class TagServiceImpl implements TagService {

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    private final UserService userService;

    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, UserService userService) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.userService = userService;
    }

    @Override
    public TagDTO save(TagDTO tagDTO) {
        log.debug("Request to save Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        //tag.setUser(userService.getUserWithAuthorities().orElseThrow());
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public TagDTO update(TagDTO tagDTO) {
        log.debug("Request to update Tag : {}", tagDTO);
        Tag tag = tagMapper.toEntity(tagDTO);
        //tag.setUser(userService.getUserWithAuthorities().orElseThrow());
        tag = tagRepository.save(tag);
        return tagMapper.toDto(tag);
    }

    @Override
    public Optional<TagDTO> partialUpdate(TagDTO tagDTO) {
        log.debug("Request to partially update Tag : {}", tagDTO);

        return tagRepository
            .findById(tagDTO.getId())
            .map(existingTag -> {
                tagMapper.partialUpdate(existingTag, tagDTO);

                return existingTag;
            })
            .map(tagRepository::save)
            .map(tagMapper::toDto);
    }

    @Override
    public Page<TagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");

        //String userId = userService.getUserWithAuthorities().orElseThrow().getId();
        //get all models from all users in case is admin
        //if(userService.getUserWithAuthorities().orElseThrow().getAuthorities().stream().filter(authority -> authority.getName().equals("ROLE_ADMIN")).findFirst().orElse(null) != null){
            return tagRepository.findAll(pageable).map(tagMapper::toDto);
        //}
        //for normal users, gets user own tags
        //return tagRepository.findByUserId(userId, pageable).map(tagMapper::toDto);
    }

    @Override
    public Optional<TagDTO> findOne(String id) {
        log.debug("Request to get Tag : {}", id);
        return tagRepository.findById(id).map(tagMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Tag : {}", id);
        tagRepository.deleteById(id);
    }
}
