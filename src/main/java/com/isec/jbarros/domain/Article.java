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

    @Field("summary")
    private String summary;

    @Field("text")
    private String text;

    @Field("file")
    private byte[] file;

    @Field("file_content_type")
    private String fileContentType;

    @DBRef
    @Field("entities")
    @JsonIgnoreProperties(value = { "tags", "articles" }, allowSetters = true)
    private Set<NamedEntity> entities = new HashSet<>();

    @DBRef
    @Field("model")
    @JsonIgnoreProperties(value = { "articles", "tags" }, allowSetters = true)
    private NLPModel model;

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

    public String getSummary() {
        return this.summary;
    }

    public Article summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Set<NamedEntity> getEntities() {
        return this.entities;
    }

    public void setEntities(Set<NamedEntity> namedEntities) {
        this.entities = namedEntities;
    }

    public Article entities(Set<NamedEntity> namedEntities) {
        this.setEntities(namedEntities);
        return this;
    }

    public Article addEntities(NamedEntity namedEntity) {
        this.entities.add(namedEntity);
        return this;
    }

    public Article removeEntities(NamedEntity namedEntity) {
        this.entities.remove(namedEntity);
        return this;
    }

    public NLPModel getModel() {
        return this.model;
    }

    public void setModel(NLPModel nLPModel) {
        this.model = nLPModel;
    }

    public Article model(NLPModel nLPModel) {
        this.setModel(nLPModel);
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
            ", summary='" + getSummary() + "'" +
            ", text='" + getText() + "'" +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            "}";
    }
}
