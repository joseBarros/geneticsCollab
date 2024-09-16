import { INamedEntity } from 'app/shared/model/named-entity.model';
import { INLPModel } from 'app/shared/model/nlp-model.model';

export interface IArticle {
  id?: string;
  title?: string;
  summary?: string | null;
  text?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  entities?: INamedEntity[] | null;
  model?: INLPModel | null;
}

export const defaultValue: Readonly<IArticle> = {};
