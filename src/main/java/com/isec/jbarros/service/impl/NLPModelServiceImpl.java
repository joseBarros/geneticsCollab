package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.repository.NLPModelRepository;
import com.isec.jbarros.service.NLPModelService;
import com.isec.jbarros.service.UserService;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.mapper.NLPModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.isec.jbarros.domain.NLPModel}.
 */
@Service
public class NLPModelServiceImpl implements NLPModelService {

    private final Logger log = LoggerFactory.getLogger(NLPModelServiceImpl.class);

    private final NLPModelRepository nLPModelRepository;

    private final NLPModelMapper nLPModelMapper;

    private static final String ENTITY_NAME = "nLPModel";

    private final String dirSeparator = FileSystems.getDefault().getSeparator();
    private final String uploadDir = Paths.get("").toAbsolutePath() + dirSeparator + "UPLOADS";

    private final String extratedDir = uploadDir + dirSeparator + "extracted";

    private final UserService userService;

    private final String adminId = "user-1";

    public NLPModelServiceImpl(NLPModelRepository nLPModelRepository, NLPModelMapper nLPModelMapper, UserService userService) {
        this.nLPModelRepository = nLPModelRepository;
        this.nLPModelMapper = nLPModelMapper;
        this.userService = userService;
    }

    @Override
    public NLPModelDTO save(NLPModelDTO nLPModelDTO) {
        log.debug("Request to save NLPModel : {}", nLPModelDTO);
        NLPModel nLPModel = nLPModelMapper.toEntity(nLPModelDTO);
        nLPModel.setUser(userService.getUserWithAuthorities().orElseThrow());
        nLPModel = nLPModelRepository.save(nLPModel);
        return nLPModelMapper.toDto(nLPModel);
    }

    @Override
    public NLPModelDTO update(NLPModelDTO nLPModelDTO) {
        log.debug("Request to update NLPModel : {}", nLPModelDTO);
        NLPModel nLPModel = nLPModelMapper.toEntity(nLPModelDTO);
        nLPModel.setUser(userService.getUserWithAuthorities().orElseThrow());
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

        String userId = userService.getUserWithAuthorities().orElseThrow().getId();
        //get all models from all users in case is admin
        if(userService.getUserWithAuthorities().orElseThrow().getAuthorities().stream().filter(authority -> authority.getName().equals("ROLE_ADMIN")).findFirst().orElse(null) != null){
            return nLPModelRepository.findAll(pageable).map(nLPModelMapper::toDto);
        }
        //for normal users, gets user own models plus the admin default ones that are for all users
        return nLPModelRepository.findByUserIdOrUserId(userId,adminId, pageable).map(nLPModelMapper::toDto);
    }

    @Override
    public Optional<NLPModelDTO> findOne(String id) {
        log.debug("Request to get NLPModel : {}", id);
        return nLPModelRepository.findById(id).map(nLPModelMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete NLPModel : {}", id);
        nLPModelRepository.deleteById(id);
    }
}
