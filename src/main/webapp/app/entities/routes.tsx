import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Article from './article';
import NamedEntity from './named-entity';
import Tag from './tag';
import NLPModel from './nlp-model';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="article/*" element={<Article />} />
        <Route path="named-entity/*" element={<NamedEntity />} />
        <Route path="tag/*" element={<Tag />} />
        <Route path="nlp-model/*" element={<NLPModel />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
