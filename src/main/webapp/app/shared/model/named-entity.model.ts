import { IArticle } from 'app/shared/model/article.model';
import { ITag } from 'app/shared/model/tag.model';

export interface INamedEntity {
  id?: string;
  text?: string;
  startChar?: number | null;
  endChar?: number | null;
  article?: IArticle | null;
  tag?: ITag | null;
}

export const defaultValue: Readonly<INamedEntity> = {};
