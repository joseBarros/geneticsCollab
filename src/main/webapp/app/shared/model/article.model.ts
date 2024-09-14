import { INamedEntity } from 'app/shared/model/named-entity.model';

export interface IArticle {
  id?: string;
  title?: string;
  summary?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  entities?: INamedEntity[] | null;
}

export const defaultValue: Readonly<IArticle> = {};
