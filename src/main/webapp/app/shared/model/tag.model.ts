import { INamedEntity } from 'app/shared/model/named-entity.model';
import { INLPModel } from 'app/shared/model/nlp-model.model';

export interface ITag {
  id?: string;
  label?: string;
  namedEntities?: INamedEntity[] | null;
  nLPModels?: INLPModel[] | null;
}

export const defaultValue: Readonly<ITag> = {};
