package com.isec.jbarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.isec.jbarros.domain.Article} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArticleDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    private String summary;

    private byte[] file;

    private String fileContentType;
    private Set<NamedEntityDTO> entities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Set<NamedEntityDTO> getEntities() {
        return entities;
    }

    public void setEntities(Set<NamedEntityDTO> entities) {
        this.entities = entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleDTO)) {
            return false;
        }

        ArticleDTO articleDTO = (ArticleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, articleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticleDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", summary='" + getSummary() + "'" +
            ", file='" + getFile() + "'" +
            ", entities=" + getEntities() +
            "}";
    }
}
