import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NamedEntity from './named-entity';
import NamedEntityDetail from './named-entity-detail';
import NamedEntityUpdate from './named-entity-update';
import NamedEntityDeleteDialog from './named-entity-delete-dialog';

const NamedEntityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NamedEntity />} />
    <Route path="new" element={<NamedEntityUpdate />} />
    <Route path=":id">
      <Route index element={<NamedEntityDetail />} />
      <Route path="edit" element={<NamedEntityUpdate />} />
      <Route path="delete" element={<NamedEntityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NamedEntityRoutes;
