import article from 'app/entities/article/article.reducer';
import namedEntity from 'app/entities/named-entity/named-entity.reducer';
import tag from 'app/entities/tag/tag.reducer';
import nLPModel from 'app/entities/nlp-model/nlp-model.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  article,
  namedEntity,
  tag,
  nLPModel,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
