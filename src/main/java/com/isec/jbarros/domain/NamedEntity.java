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
    private String startChar;

    @Field("end_char")
    private String endChar;

    @DBRef
    @Field("tags")
    @JsonIgnoreProperties(value = { "namedEntities", "nLPModels" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    @DBRef
    @Field("articles")
    @JsonIgnoreProperties(value = { "entities", "model" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

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

    public String getStartChar() {
        return this.startChar;
    }

    public NamedEntity startChar(String startChar) {
        this.setStartChar(startChar);
        return this;
    }

    public void setStartChar(String startChar) {
        this.startChar = startChar;
    }

    public String getEndChar() {
        return this.endChar;
    }

    public NamedEntity endChar(String endChar) {
        this.setEndChar(endChar);
        return this;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public NamedEntity tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public NamedEntity addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public NamedEntity removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.removeEntities(this));
        }
        if (articles != null) {
            articles.forEach(i -> i.addEntities(this));
        }
        this.articles = articles;
    }

    public NamedEntity articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public NamedEntity addArticle(Article article) {
        this.articles.add(article);
        article.getEntities().add(this);
        return this;
    }

    public NamedEntity removeArticle(Article article) {
        this.articles.remove(article);
        article.getEntities().remove(this);
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
            ", startChar='" + getStartChar() + "'" +
            ", endChar='" + getEndChar() + "'" +
            "}";
    }
}
