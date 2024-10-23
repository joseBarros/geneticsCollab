package com.isec.jbarros.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Article.
 */
@Document(collection = "article")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("text")
    private String text;

    @Field("file")
    private byte[] file;

    @Field("file_content_type")
    private String fileContentType;

    @Field("interactions_image")
    private byte[] interactionsImage;

    @Field("interactions_image_content_type")
    private String interactionsImageContentType;

    @DBRef
    @Field("namedEntities")
    @JsonIgnoreProperties(value = { "article", "tag" }, allowSetters = true)
    private Set<NamedEntity> namedEntities = new HashSet<>();

    @DBRef
    @Field("nlpModel")
    @JsonIgnoreProperties(value = { "tags", "articles" }, allowSetters = true)
    private NLPModel nlpModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Article id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Article title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public Article text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getFile() {
        return this.file;
    }

    public Article file(byte[] file) {
        this.setFile(file);
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public Article fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public byte[] getInteractionsImage() {
        return this.interactionsImage;
    }

    public Article interactionsImage(byte[] interactionsImage) {
        this.setInteractionsImage(interactionsImage);
        return this;
    }

    public void setInteractionsImage(byte[] interactionsImage) {
        this.interactionsImage = interactionsImage;
    }

    public String getInteractionsImageContentType() {
        return this.interactionsImageContentType;
    }

    public Article interactionsImageContentType(String interactionsImageContentType) {
        this.interactionsImageContentType = interactionsImageContentType;
        return this;
    }

    public void setInteractionsImageContentType(String interactionsImageContentType) {
        this.interactionsImageContentType = interactionsImageContentType;
    }

    public Set<NamedEntity> getNamedEntities() {
        return this.namedEntities;
    }

    public void setNamedEntities(Set<NamedEntity> namedEntities) {
        if (this.namedEntities != null) {
            this.namedEntities.forEach(i -> i.setArticle(null));
        }
        if (namedEntities != null) {
            namedEntities.forEach(i -> i.setArticle(this));
        }
        this.namedEntities = namedEntities;
    }

    public Article namedEntities(Set<NamedEntity> namedEntities) {
        this.setNamedEntities(namedEntities);
        return this;
    }

    public Article addNamedEntities(NamedEntity namedEntity) {
        this.namedEntities.add(namedEntity);
        namedEntity.setArticle(this);
        return this;
    }

    public Article removeNamedEntities(NamedEntity namedEntity) {
        this.namedEntities.remove(namedEntity);
        namedEntity.setArticle(null);
        return this;
    }

    public NLPModel getNlpModel() {
        return this.nlpModel;
    }

    public void setNlpModel(NLPModel nLPModel) {
        this.nlpModel = nLPModel;
    }

    public Article nlpModel(NLPModel nLPModel) {
        this.setNlpModel(nLPModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return getId() != null && getId().equals(((Article) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", text='" + getText() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", interactionsImage='" + getInteractionsImage() + "'" +
            ", interactionsImageContentType='" + getInteractionsImageContentType() + "'" +
            "}";
    }
}
