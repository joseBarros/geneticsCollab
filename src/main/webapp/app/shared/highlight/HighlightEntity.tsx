import React from 'react';
import './HighlightEntity.css';

interface INamedEntity {
  id?: string;
  text?: string;
  startChar?: string | null;
  endChar?: string | null;
  tags?: ITag[] | null;
}

interface ITag {
  id?: string;
  label?: string; // Changed from `name` to `label`
  namedEntities?: INamedEntity[] | null;
}

interface HighlightEntityProps {
  text: string;
  entities?: INamedEntity[] | null;
}

const HighlightEntity: React.FC<HighlightEntityProps> = ({ text, entities }) => {
  // Safeguard if entities are null or undefined
  if (!entities || entities.length === 0) {
    return <div>{text}</div>;
  }

  const getHighlightedText = (): React.ReactNode[] => {
    let lastIndex = 0;
    const highlightedText: React.ReactNode[] = [];

    entities.forEach((entity, idx) => {
      // eslint-disable-next-line no-console
      console.log(entity)
      // If startChar or endChar are null, skip this entity
      if (!entity.startChar || !entity.endChar) {
        return;
      }

      const start = parseInt(entity.startChar, 10);
      const end = parseInt(entity.endChar, 10);
      const tagLabels = entity.tags ? entity.tags.map(tag => tag.label).join(", ") : "Unknown";

      // Push the text before the entity
      if (lastIndex < start) {
        highlightedText.push(text.slice(lastIndex, start));  // Text before the entity
      }

      // Push the highlighted entity with the tag(s)
      highlightedText.push(
        <span key={idx} className="highlight">
          {text.slice(start, end)} <strong className="tag">{tagLabels}</strong>
        </span>
      );

      lastIndex = end; // Move lastIndex to after the entity
    });

    // Push the remaining text after the last entity
    if (lastIndex < text.length) {
      highlightedText.push(text.slice(lastIndex));
    }

    return highlightedText;
  };

  return <div>{getHighlightedText()}</div>;
};

export default HighlightEntity;
