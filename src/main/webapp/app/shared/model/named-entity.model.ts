import { ITag } from 'app/shared/model/tag.model';
import { IArticle } from 'app/shared/model/article.model';

export interface INamedEntity {
  id?: string;
  text?: string;
  startChar?: string | null;
  endChar?: string | null;
  tags?: ITag[] | null;
  articles?: IArticle[] | null;
}

export const defaultValue: Readonly<INamedEntity> = {};
