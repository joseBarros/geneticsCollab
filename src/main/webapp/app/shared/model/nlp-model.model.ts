import { ITag } from 'app/shared/model/tag.model';

export interface INLPModel {
  id?: string;
  name?: string;
  framework?: string | null;
  url?: string | null;
  notes?: string | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<INLPModel> = {};
