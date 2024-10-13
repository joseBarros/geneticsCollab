import { INamedEntity } from 'app/shared/model/named-entity.model';
import { INLPModel } from 'app/shared/model/nlp-model.model';

export interface IArticle {
  id?: string;
  title?: string;
  text?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  interactionsImageContentType?: string | null;
  interactionsImage?: string | null;
  entities?: INamedEntity[] | null;
  model?: INLPModel | null;
}

export const defaultValue: Readonly<IArticle> = {};
