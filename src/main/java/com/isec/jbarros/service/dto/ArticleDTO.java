package com.isec.jbarros.service.dto;

import com.isec.jbarros.domain.NamedEntity;
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

    private String text;

    private byte[] file;

    private String fileContentType;
    private byte[] interactionsImage;

    private String interactionsImageContentType;
    private NLPModelDTO nlpModel;

    private Set<NamedEntityDTO> namedEntities = new HashSet<>();

    public Set<NamedEntityDTO> getNamedEntities() {
        return namedEntities;
    }

    public void setNamedEntities(Set<NamedEntityDTO> namedEntities) {
        this.namedEntities = namedEntities;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public byte[] getInteractionsImage() {
        return interactionsImage;
    }

    public void setInteractionsImage(byte[] interactionsImage) {
        this.interactionsImage = interactionsImage;
    }

    public String getInteractionsImageContentType() {
        return interactionsImageContentType;
    }

    public void setInteractionsImageContentType(String interactionsImageContentType) {
        this.interactionsImageContentType = interactionsImageContentType;
    }

    public NLPModelDTO getNlpModel() {
        return nlpModel;
    }

    public void setNlpModel(NLPModelDTO nlpModel) {
        this.nlpModel = nlpModel;
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
            ", text='" + getText() + "'" +
            ", file='" + getFile() + "'" +
            ", interactionsImage='" + getInteractionsImage() + "'" +
            ", entities=" + getNamedEntities() +
            ", model=" + getNlpModel() +
            "}";
    }
}
