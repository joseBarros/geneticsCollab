import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './named-entity.reducer';

export const NamedEntityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const namedEntityEntity = useAppSelector(state => state.namedEntity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="namedEntityDetailsHeading">
          <Translate contentKey="geneticsCollabApp.namedEntity.detail.title">NamedEntity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{namedEntityEntity.id}</dd>
          <dt>
            <span id="text">
              <Translate contentKey="geneticsCollabApp.namedEntity.text">Text</Translate>
            </span>
          </dt>
          <dd>{namedEntityEntity.text}</dd>
          <dt>
            <span id="startChar">
              <Translate contentKey="geneticsCollabApp.namedEntity.startChar">Start Char</Translate>
            </span>
          </dt>
          <dd>{namedEntityEntity.startChar}</dd>
          <dt>
            <span id="endChar">
              <Translate contentKey="geneticsCollabApp.namedEntity.endChar">End Char</Translate>
            </span>
          </dt>
          <dd>{namedEntityEntity.endChar}</dd>
          <dt>
            <Translate contentKey="geneticsCollabApp.namedEntity.tags">Tags</Translate>
          </dt>
          <dd>
            {namedEntityEntity.tags
              ? namedEntityEntity.tags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.label}</a>
                    {namedEntityEntity.tags && i === namedEntityEntity.tags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/named-entity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/named-entity/${namedEntityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NamedEntityDetail;
