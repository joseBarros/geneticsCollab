package com.isec.jbarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.isec.jbarros.domain.NamedEntity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NamedEntityDTO implements Serializable {

    private String id;

    @NotNull
    private String text;

    private String startChar;

    private String endChar;

    private Set<TagDTO> tags = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStartChar() {
        return startChar;
    }

    public void setStartChar(String startChar) {
        this.startChar = startChar;
    }

    public String getEndChar() {
        return endChar;
    }

    public void setEndChar(String endChar) {
        this.endChar = endChar;
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
        if (!(o instanceof NamedEntityDTO)) {
            return false;
        }

        NamedEntityDTO namedEntityDTO = (NamedEntityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, namedEntityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NamedEntityDTO{" +
            "id='" + getId() + "'" +
            ", text='" + getText() + "'" +
            ", startChar='" + getStartChar() + "'" +
            ", endChar='" + getEndChar() + "'" +
            ", tags=" + getTags() +
            "}";
    }
}
