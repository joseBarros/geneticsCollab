package com.isec.jbarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.isec.jbarros.domain.NLPModel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NLPModelDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String framework;

    private String url;

    private String notes;

    private Set<TagDTO> tags = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NLPModelDTO)) {
            return false;
        }

        NLPModelDTO nLPModelDTO = (NLPModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nLPModelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NLPModelDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", framework='" + getFramework() + "'" +
            ", url='" + getUrl() + "'" +
            ", notes='" + getNotes() + "'" +
            ", tags=" + getTags() +
            "}";
    }
}
