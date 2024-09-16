import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nlp-model.reducer';

export const NLPModelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nLPModelEntity = useAppSelector(state => state.nLPModel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nLPModelDetailsHeading">
          <Translate contentKey="geneticsCollabApp.nLPModel.detail.title">NLPModel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nLPModelEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="geneticsCollabApp.nLPModel.name">Name</Translate>
            </span>
          </dt>
          <dd>{nLPModelEntity.name}</dd>
          <dt>
            <span id="framework">
              <Translate contentKey="geneticsCollabApp.nLPModel.framework">Framework</Translate>
            </span>
          </dt>
          <dd>{nLPModelEntity.framework}</dd>
          <dt>
            <span id="path">
              <Translate contentKey="geneticsCollabApp.nLPModel.path">Path</Translate>
            </span>
          </dt>
          <dd>{nLPModelEntity.path}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="geneticsCollabApp.nLPModel.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{nLPModelEntity.notes}</dd>
          <dt>
            <Translate contentKey="geneticsCollabApp.nLPModel.tags">Tags</Translate>
          </dt>
          <dd>
            {nLPModelEntity.tags
              ? nLPModelEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.label}</a>
                    {nLPModelEntity.tags && i === nLPModelEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/nlp-model" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nlp-model/${nLPModelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NLPModelDetail;
