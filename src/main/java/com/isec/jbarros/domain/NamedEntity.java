package com.isec.jbarros.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A NamedEntity.
 */
@Document(collection = "named_entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NamedEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("text")
    private String text;

    @Field("start_char")
    private Integer startChar;

    @Field("end_char")
    private Integer endChar;

    @DBRef
    @Field("article")
    @JsonIgnoreProperties(value = { "namedEntities", "nlpModel" }, allowSetters = true)
    private Article article;

    @DBRef
    @Field("tag")
    @JsonIgnoreProperties(value = { "namedEntities", "nlpModel" }, allowSetters = true)
    private Tag tag;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public NamedEntity id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public NamedEntity text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStartChar() {
        return this.startChar;
    }

    public NamedEntity startChar(Integer startChar) {
        this.setStartChar(startChar);
        return this;
    }

    public void setStartChar(Integer startChar) {
        this.startChar = startChar;
    }

    public Integer getEndChar() {
        return this.endChar;
    }

    public NamedEntity endChar(Integer endChar) {
        this.setEndChar(endChar);
        return this;
    }

    public void setEndChar(Integer endChar) {
        this.endChar = endChar;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public NamedEntity article(Article article) {
        this.setArticle(article);
        return this;
    }

    public Tag getTag() {
        return this.tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public NamedEntity tag(Tag tag) {
        this.setTag(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NamedEntity)) {
            return false;
        }
        return getId() != null && getId().equals(((NamedEntity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NamedEntity{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", startChar=" + getStartChar() +
            ", endChar=" + getEndChar() +
            "}";
    }
}
