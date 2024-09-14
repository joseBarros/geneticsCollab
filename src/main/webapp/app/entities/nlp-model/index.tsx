import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NLPModel from './nlp-model';
import NLPModelDetail from './nlp-model-detail';
import NLPModelUpdate from './nlp-model-update';
import NLPModelDeleteDialog from './nlp-model-delete-dialog';

const NLPModelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NLPModel />} />
    <Route path="new" element={<NLPModelUpdate />} />
    <Route path=":id">
      <Route index element={<NLPModelDetail />} />
      <Route path="edit" element={<NLPModelUpdate />} />
      <Route path="delete" element={<NLPModelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NLPModelRoutes;
