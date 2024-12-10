package com.isec.jbarros.service.impl;

import com.isec.jbarros.domain.NLPModel;
import com.isec.jbarros.repository.NLPModelRepository;
import com.isec.jbarros.service.NLPModelService;
import com.isec.jbarros.service.dto.NLPModelDTO;
import com.isec.jbarros.service.mapper.NLPModelMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.isec.jbarros.web.rest.NLPModelResource;
import com.isec.jbarros.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

//    @Override
//    @Async
//    public Future<NLPModelDTO> processNLPModelAsync(NLPModelDTO nLPModelDTO, MultipartFile file) {
//        try {
//            // Processing logic (e.g., saving the file and updating the DTO)
//            nLPModelDTO = uploadNLPModelFile(nLPModelDTO, file);
//            NLPModelDTO result = save(nLPModelDTO); // Replace with actual save method
//            log.debug("Model processed and saved with ID: {}", result.getId());
//            return new AsyncResult<>(result);
//        } catch (Exception e) {
//            log.error("Error processing NLPModel: ", e);
//            return new AsyncResult<>(null);
//        }
//    }
//
//    @Override
//    public NLPModelDTO uploadNLPModelFile(NLPModelDTO nLPModelDTO, MultipartFile file) {
//        log.debug("REST request to upload file : {}", file.getOriginalFilename());
//
//        if (file.isEmpty()) {
//            throw new BadRequestAlertException("File is empty", ENTITY_NAME, "empty");
//        }
//
//        try {
//            // Create directory if it does not exist
//            Path uploadPath = Paths.get(uploadDir);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            // Save the file
//            String filePath = uploadDir + File.separator + file.getOriginalFilename();
//            Path path = Paths.get(filePath);
//            Files.write(path, file.getBytes());
//            String extractDir = extratedDir + dirSeparator + file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
//            // Extract ZIP file contents
//            extractZipFile(filePath, extractDir);
//
//            // Update the NLPModel with the file path
//            nLPModelDTO.setPath(extractDir);
//
//            log.debug("File uploaded successfully: " + filePath);
//        } catch (IOException e) {
//            log.error("Failed to upload file", e);
//            throw new BadRequestAlertException("File upload failed: " + e.getMessage(), ENTITY_NAME, e.getMessage());
//        }
//        return nLPModelDTO;
//    }
//
//    @Override
//    public void extractZipFile(String zipFilePath, String destDir) throws IOException {
//        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)))) {
//            ZipEntry entry = zipIn.getNextEntry();
//            while (entry != null) {
//                Path entryPath = Paths.get(destDir, entry.getName()).normalize();
//
//                // Check for directory traversal vulnerability
//                if (!entryPath.startsWith(Paths.get(destDir))) {
//                    throw new IOException("Entry is outside of the target dir: " + entry.getName());
//                }
//
//                if (entry.isDirectory()) {
//                    Files.createDirectories(entryPath);
//                } else {
//                    // Ensure parent directories exist
//                    Files.createDirectories(entryPath.getParent());
//                    System.out.println("Extracting " + entryPath);
//
//                    // Extract the file
//                    try (FileOutputStream fos = new FileOutputStream(entryPath.toFile())) {
//                        byte[] buffer = new byte[4096];
//                        int len;
//                        while ((len = zipIn.read(buffer)) > 0) {
//                            fos.write(buffer, 0, len);
//                        }
//                    }
//                }
//                zipIn.closeEntry();
//                entry = zipIn.getNextEntry();
//            }
//        }
//    }
}
