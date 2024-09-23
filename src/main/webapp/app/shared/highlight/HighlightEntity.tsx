import React, { useMemo, useState } from 'react';
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
  label?: string;
}

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
    const normalizedLabel = normalizeTagLabel(tagLabel);

    // If the color for this entity type is already generated, use it
    if (colorMap[normalizedLabel]) {
      return colorMap[normalizedLabel];
    }

    // Otherwise, generate a new color and store it in the map
    const color = hashStringToColor(normalizedLabel);
    setColorMap(prevColorMap => ({
      ...prevColorMap,
      [normalizedLabel]: color
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

      const start = parseInt(entity.startChar, 10);
      const end = parseInt(entity.endChar, 10);

      if (lastIndex < start) {
        highlightedText.push(text.slice(lastIndex, start));
      }

      // Map over tags and normalize the label to handle IOB format
      const tagLabels = entity.tags?.map(tag => normalizeTagLabel(tag?.label || "Unknown")).join(", ") || "No Tags";
      const entityColor = getEntityColor(tagLabels);

      // Push the highlighted entity with the tag(s) and color
      highlightedText.push(
        <span key={idx} className="highlight" style={{ backgroundColor: entityColor }}>
          {text.slice(start, end)} <strong className="tag">[{tagLabels}]</strong>
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
