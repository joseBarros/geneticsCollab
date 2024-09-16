import { IArticle } from 'app/shared/model/article.model';
import { ITag } from 'app/shared/model/tag.model';

export interface INLPModel {
  id?: string;
  name?: string;
  framework?: string | null;
  path?: string | null;
  notes?: string | null;
  articles?: IArticle[] | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<INLPModel> = {};
