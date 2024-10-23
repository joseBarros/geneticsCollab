import { ITag } from 'app/shared/model/tag.model';
import { IArticle } from 'app/shared/model/article.model';

export interface INLPModel {
  id?: string;
  name?: string;
  framework?: string | null;
  path?: string | null;
  notes?: string | null;
  tags?: ITag[] | null;
  articles?: IArticle[] | null;
}

export const defaultValue: Readonly<INLPModel> = {};
