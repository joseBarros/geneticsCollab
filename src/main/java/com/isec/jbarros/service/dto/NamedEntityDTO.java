package com.isec.jbarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isec.jbarros.domain.NamedEntity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NamedEntityDTO implements Serializable {

    private String id;

    @NotNull
    private String text;

    private Integer startChar;

    private Integer endChar;

    private ArticleDTO article;

    private TagDTO tag;

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

    public Integer getStartChar() {
        return startChar;
    }

    public void setStartChar(Integer startChar) {
        this.startChar = startChar;
    }

    public Integer getEndChar() {
        return endChar;
    }

    public void setEndChar(Integer endChar) {
        this.endChar = endChar;
    }

    public ArticleDTO getArticle() {
        return article;
    }

    public void setArticle(ArticleDTO article) {
        this.article = article;
    }

    public TagDTO getTag() {
        return tag;
    }

    public void setTag(TagDTO tag) {
        this.tag = tag;
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
            ", startChar=" + getStartChar() +
            ", endChar=" + getEndChar() +
            ", article=" + getArticle() +
            ", tag=" + getTag() +
            "}";
    }
}
