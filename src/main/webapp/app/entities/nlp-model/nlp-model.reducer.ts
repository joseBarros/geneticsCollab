import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { INLPModel, defaultValue } from 'app/shared/model/nlp-model.model';

const initialState: EntityState<INLPModel> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/nlp-models';

// Actions

export const getEntities = createAsyncThunk('nLPModel/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `page=${page}&size=${size}&sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<INLPModel[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'nLPModel/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<INLPModel>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'nLPModel/create_entity',
  async ({ entity, file }: { entity: INLPModel; file: File }, thunkAPI) => {

    // eslint-disable-next-line no-console
    console.log("nLPModel/create_entity")

    const formData = new FormData();
    formData.append('file', file);
    formData.append('nLPModelDTO', new Blob([JSON.stringify(cleanEntity(entity))], { type: 'application/json' }));

    // eslint-disable-next-line no-console
    console.log(formData)

    // eslint-disable-next-line no-console
    console.log(file)
    // eslint-disable-next-line no-console
    console.log(cleanEntity(entity))

    const result = await axios.post<INLPModel>(apiUrl, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
      timeout: 300000, // 5 minutes
    });
    // eslint-disable-next-line no-console
    console.log(result)

    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
    'nLPModel/update_entity',
    async ({ entity, file }: { entity: INLPModel; file: File }, thunkAPI) => {

      const formData = new FormData();
      // Append the file or an empty Blob if no file is provided
      formData.append('file', file || new Blob([], { type: 'application/octet-stream' }));
      formData.append('nLPModelDTO', new Blob([JSON.stringify(cleanEntity(entity))], { type: 'application/json' }));

      const result = await axios.put<INLPModel>(`${apiUrl}/${entity.id}` , formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        timeout: 300000, // 5 minutes
      });

      thunkAPI.dispatch(getEntities({}));
      return result;
    },
    { serializeError: serializeAxiosError },
  );

export const partialUpdateEntity = createAsyncThunk(
  'nLPModel/partial_update_entity',
  async (entity: INLPModel, thunkAPI) => {
    const result = await axios.patch<INLPModel>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'nLPModel/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<INLPModel>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const NLPModelSlice = createEntitySlice({
  name: 'nLPModel',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = NLPModelSlice.actions;

// Reducer
export default NLPModelSlice.reducer;
