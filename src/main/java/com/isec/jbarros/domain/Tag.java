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
 * A Tag.
 */
@Document(collection = "tag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("label")
    private String label;

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

    public Tag id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public Tag label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<NamedEntity> getNamedEntities() {
        return this.namedEntities;
    }

    public void setNamedEntities(Set<NamedEntity> namedEntities) {
        if (this.namedEntities != null) {
            this.namedEntities.forEach(i -> i.setTag(null));
        }
        if (namedEntities != null) {
            namedEntities.forEach(i -> i.setTag(this));
        }
        this.namedEntities = namedEntities;
    }

    public Tag namedEntities(Set<NamedEntity> namedEntities) {
        this.setNamedEntities(namedEntities);
        return this;
    }

    public Tag addNamedEntities(NamedEntity namedEntity) {
        this.namedEntities.add(namedEntity);
        namedEntity.setTag(this);
        return this;
    }

    public Tag removeNamedEntities(NamedEntity namedEntity) {
        this.namedEntities.remove(namedEntity);
        namedEntity.setTag(null);
        return this;
    }

    public NLPModel getNlpModel() {
        return this.nlpModel;
    }

    public void setNlpModel(NLPModel nLPModel) {
        this.nlpModel = nLPModel;
    }

    public Tag nlpModel(NLPModel nLPModel) {
        this.setNlpModel(nLPModel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return getId() != null && getId().equals(((Tag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
