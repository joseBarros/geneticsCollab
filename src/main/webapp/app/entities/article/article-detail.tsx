import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article.reducer';

import HighlightEntity from 'app/shared/highlight/HighlightEntity';

export const ArticleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleEntity = useAppSelector(state => state.article.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="articleDetailsHeading">
          <Translate contentKey="geneticsCollabApp.article.detail.title">Article</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{articleEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="geneticsCollabApp.article.title">Title</Translate>
            </span>
          </dt>
          <dd>{articleEntity.title}</dd>
          <dt>
            <span id="file">
              <Translate contentKey="geneticsCollabApp.article.file">File</Translate>
            </span>
          </dt>
          <dd>
            {articleEntity.file ? (
              <div>
                {articleEntity.fileContentType ? (
                  <a onClick={openFile(articleEntity.fileContentType, articleEntity.file)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {articleEntity.fileContentType}, {byteSize(articleEntity.file)}
                </span>
              </div>
            ) : null}
          </dd>
          {/*          <dt>
            <span id="summary">
              <Translate contentKey="geneticsCollabApp.article.summary">Summary</Translate>
            </span>
          </dt>
          <dd>{articleEntity.summary}</dd>*/}
          <dt>
            <span id="text">
              <Translate contentKey="geneticsCollabApp.article.text">Text</Translate>
            </span>
          </dt>
          <dd><HighlightEntity text={articleEntity.text || ''} entities={articleEntity.entities || []} /></dd>
          <dt>
            <Translate contentKey="geneticsCollabApp.article.entities">Entities</Translate>
          </dt>
          <dd>
            {articleEntity.entities
              ? articleEntity.entities.map((val, i) => (
                <span key={val.id}>
                    <a>{val.text}</a>
                  {articleEntity.entities && i === articleEntity.entities.length - 1 ? '' : ', '}
                  </span>
              ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="geneticsCollabApp.article.model">Model</Translate>
          </dt>
          <dd>{articleEntity.model ? articleEntity.model.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/article" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/article/${articleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArticleDetail;
