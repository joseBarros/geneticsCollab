package com.isec.jbarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isec.jbarros.domain.Tag} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TagDTO implements Serializable {

    private String id;

    @NotNull
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagDTO)) {
            return false;
        }

        TagDTO tagDTO = (TagDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tagDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagDTO{" +
            "id='" + getId() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
