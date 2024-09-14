package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.repository.NLPModelRepository;
import com.isec.jbarros.service.NLPModelService;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.mapper.NLPModelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.isec.jbarros.domain.NLPModel}.
 */
@Service
public class NLPModelServiceImpl implements NLPModelService {

    private final Logger log = LoggerFactory.getLogger(NLPModelServiceImpl.class);

    private final NLPModelRepository nLPModelRepository;

    private final NLPModelMapper nLPModelMapper;

    public NLPModelServiceImpl(NLPModelRepository nLPModelRepository, NLPModelMapper nLPModelMapper) {
        this.nLPModelRepository = nLPModelRepository;
        this.nLPModelMapper = nLPModelMapper;
    }

    @Override
    public NLPModelDTO save(NLPModelDTO nLPModelDTO) {
        log.debug("Request to save NLPModel : {}", nLPModelDTO);
        NLPModel nLPModel = nLPModelMapper.toEntity(nLPModelDTO);
        nLPModel = nLPModelRepository.save(nLPModel);
        return nLPModelMapper.toDto(nLPModel);
    }

    @Override
    public NLPModelDTO update(NLPModelDTO nLPModelDTO) {
        log.debug("Request to update NLPModel : {}", nLPModelDTO);
        NLPModel nLPModel = nLPModelMapper.toEntity(nLPModelDTO);
        nLPModel = nLPModelRepository.save(nLPModel);
        return nLPModelMapper.toDto(nLPModel);
    }

    @Override
    public Optional<NLPModelDTO> partialUpdate(NLPModelDTO nLPModelDTO) {
        log.debug("Request to partially update NLPModel : {}", nLPModelDTO);

        return nLPModelRepository
            .findById(nLPModelDTO.getId())
            .map(existingNLPModel -> {
                nLPModelMapper.partialUpdate(existingNLPModel, nLPModelDTO);

                return existingNLPModel;
            })
            .map(nLPModelRepository::save)
            .map(nLPModelMapper::toDto);
    }

    @Override
    public Page<NLPModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NLPModels");
        return nLPModelRepository.findAll(pageable).map(nLPModelMapper::toDto);
    }

    public Page<NLPModelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return nLPModelRepository.findAllWithEagerRelationships(pageable).map(nLPModelMapper::toDto);
    }

    @Override
    public Optional<NLPModelDTO> findOne(String id) {
        log.debug("Request to get NLPModel : {}", id);
        return nLPModelRepository.findOneWithEagerRelationships(id).map(nLPModelMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete NLPModel : {}", id);
        nLPModelRepository.deleteById(id);
    }
}
