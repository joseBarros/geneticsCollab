package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.NamedEntity;
import com.isec.jbarros.repository.NamedEntityRepository;
import com.isec.jbarros.service.NamedEntityService;
import com.isec.jbarros.service.dto.NamedEntityDTO;
import com.isec.jbarros.service.mapper.NamedEntityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.isec.jbarros.domain.NamedEntity}.
 */
@Service
public class NamedEntityServiceImpl implements NamedEntityService {

    private final Logger log = LoggerFactory.getLogger(NamedEntityServiceImpl.class);

    private final NamedEntityRepository namedEntityRepository;

    private final NamedEntityMapper namedEntityMapper;

    public NamedEntityServiceImpl(NamedEntityRepository namedEntityRepository, NamedEntityMapper namedEntityMapper) {
        this.namedEntityRepository = namedEntityRepository;
        this.namedEntityMapper = namedEntityMapper;
    }

    @Override
    public NamedEntityDTO save(NamedEntityDTO namedEntityDTO) {
        log.debug("Request to save NamedEntity : {}", namedEntityDTO);
        NamedEntity namedEntity = namedEntityMapper.toEntity(namedEntityDTO);
        namedEntity = namedEntityRepository.save(namedEntity);
        return namedEntityMapper.toDto(namedEntity);
    }

    @Override
    public NamedEntityDTO update(NamedEntityDTO namedEntityDTO) {
        log.debug("Request to update NamedEntity : {}", namedEntityDTO);
        NamedEntity namedEntity = namedEntityMapper.toEntity(namedEntityDTO);
        namedEntity = namedEntityRepository.save(namedEntity);
        return namedEntityMapper.toDto(namedEntity);
    }

    @Override
    public Optional<NamedEntityDTO> partialUpdate(NamedEntityDTO namedEntityDTO) {
        log.debug("Request to partially update NamedEntity : {}", namedEntityDTO);

        return namedEntityRepository
            .findById(namedEntityDTO.getId())
            .map(existingNamedEntity -> {
                namedEntityMapper.partialUpdate(existingNamedEntity, namedEntityDTO);

                return existingNamedEntity;
            })
            .map(namedEntityRepository::save)
            .map(namedEntityMapper::toDto);
    }

    @Override
    public Page<NamedEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NamedEntities");
        return namedEntityRepository.findAll(pageable).map(namedEntityMapper::toDto);
    }

    public Page<NamedEntityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return namedEntityRepository.findAllWithEagerRelationships(pageable).map(namedEntityMapper::toDto);
    }

    @Override
    public Optional<NamedEntityDTO> findOne(String id) {
        log.debug("Request to get NamedEntity : {}", id);
        return namedEntityRepository.findOneWithEagerRelationships(id).map(namedEntityMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete NamedEntity : {}", id);
        namedEntityRepository.deleteById(id);
    }
}
