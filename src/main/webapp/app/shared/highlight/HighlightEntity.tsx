import React, { useMemo, useState } from 'react';
import './HighlightEntity.css';
import { INamedEntity } from 'app/shared/model/named-entity.model';

interface HighlightEntityProps {
  text: string;
  entities?: INamedEntity[] | null;
}

// Utility function to normalize entity types by removing IOB prefixes
const normalizeTagLabel = (label: string): string => {
  return label.replace(/^B-|^I-/, '');  // Removes the "B-" or "I-" prefix from the tag
};

// Hash function to generate a consistent hash for a string (entity label)
const hashStringToColor = (str: string): string => {
  let hash = 0;
  for (let i = 0; i < str.length; i++) {
    // eslint-disable-next-line no-bitwise
    hash = str.charCodeAt(i) + ((hash << 5) - hash); // Simple string hashing
  }
  const color = `hsl(${hash % 360}, 70%, 80%)`; // HSL color based on hash value
  return color;
};

const HighlightEntity: React.FC<HighlightEntityProps> = ({ text, entities }) => {
  const [colorMap, setColorMap] = useState<Record<string, string>>({});

  const getEntityColor = (tagLabel: string): string => {
    //const normalizedLabel = normalizeTagLabel(tagLabel);

    // If the color for this entity type is already generated, use it
    if (colorMap[tagLabel]) {
      return colorMap[tagLabel];
    }

    // Otherwise, generate a new color and store it in the map
    const color = hashStringToColor(tagLabel);
    setColorMap(prevColorMap => ({
      ...prevColorMap,
      [tagLabel]: color
    }));

    return color;
  };

  const getHighlightedText = (): React.ReactNode[] => {
    let lastIndex = 0;
    const highlightedText: React.ReactNode[] = [];

    entities?.forEach((entity, idx) => {
      if (!entity.startChar || !entity.endChar) {
        console.warn(`Skipping entity ${idx} due to missing startChar or endChar`);
        return;
      }

      const start = entity.startChar;
      const end = entity.endChar;

      if (lastIndex < start) {
        highlightedText.push(text.slice(lastIndex, start));
      }

      // Map over tags and normalize the label to handle IOB format
      //const tagLabels = entity.tags?.map(tag => normalizeTagLabel(tag?.label || "Unknown")).join(", ") || "No Tags";
      const tagLabels = entity.tag ? entity.tag?.label : "No Tag";
      const entityColor = getEntityColor(tagLabels);

      // Push the highlighted entity with the tag(s) and color
      highlightedText.push(
        <span key={idx} className="highlight" style={{ backgroundColor: entityColor }}>
          {text.slice(start, end)} <strong className="tag">{tagLabels}</strong>
        </span>
      );

      lastIndex = end;
    });

    if (lastIndex < text.length) {
      highlightedText.push(text.slice(lastIndex));
    }

    return highlightedText;
  };

  return <div>{getHighlightedText()}</div>;
};

export default HighlightEntity;
