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
 * A NLPModel.
 */
@Document(collection = "nlp_model")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NLPModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("framework")
    private String framework;

    @Field("path")
    private String path;

    @Field("notes")
    private String notes;

    @DBRef
    @Field("article")
    @JsonIgnoreProperties(value = { "entities", "model" }, allowSetters = true)
    private Set<Article> articles = new HashSet<>();

    @DBRef
    @Field("tags")
    @JsonIgnoreProperties(value = { "namedEntities", "nLPModels" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public NLPModel id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public NLPModel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFramework() {
        return this.framework;
    }

    public NLPModel framework(String framework) {
        this.setFramework(framework);
        return this;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getPath() {
        return this.path;
    }

    public NLPModel path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNotes() {
        return this.notes;
    }

    public NLPModel notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setModel(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setModel(this));
        }
        this.articles = articles;
    }

    public NLPModel articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public NLPModel addArticle(Article article) {
        this.articles.add(article);
        article.setModel(this);
        return this;
    }

    public NLPModel removeArticle(Article article) {
        this.articles.remove(article);
        article.setModel(null);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public NLPModel tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public NLPModel addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public NLPModel removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NLPModel)) {
            return false;
        }
        return getId() != null && getId().equals(((NLPModel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NLPModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", framework='" + getFramework() + "'" +
            ", path='" + getPath() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
