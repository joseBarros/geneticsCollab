import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col, Table } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './article.reducer';

import HighlightEntity from 'app/shared/highlight/HighlightEntity';

import './article.css';
import { ITag } from 'app/shared/model/tag.model';

export const ArticleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  // Local state to keep track of filtered tags
  const [filteredTags, setFilteredTags] = useState<ITag[]>([]);

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const articleEntity = useAppSelector(state => state.article.entity);
  // eslint-disable-next-line no-console
  console.log(articleEntity)
  // When the article data is loaded, set the initial filtered tags
  useEffect(() => {
    if (articleEntity.nlpModel && articleEntity.nlpModel.tags) {
      setFilteredTags(articleEntity.nlpModel.tags); // Initially, show all tags
    }
  }, [articleEntity]);

  // Remove a tag from the UI (without deleting it from the DB)
  const handleTagRemove = (tagId: string) => {
    // Remove the tag from filteredTags (for UI purposes only)
    const remainingTags = filteredTags.filter(tag => tag.id !== tagId);
    setFilteredTags(remainingTags);
  };


// Filter entities based on the remaining tags
  const filteredEntities = articleEntity.namedEntities
    ? articleEntity.namedEntities.filter(entity =>
      filteredTags.some(filteredTag => filteredTag.id === entity.tag?.id)
    )
    : [];

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
            <Translate contentKey="geneticsCollabApp.article.nlpModel">Model</Translate>
          </dt>
          <dd>{articleEntity.nlpModel ? articleEntity.nlpModel.name : <span>No NLP model selected</span>}</dd>
          {articleEntity.nlpModel && filteredTags.length > 0 && (
            <dd>
              {filteredTags.map(tag => (
                <span key={tag.id} className="badge badge-info" style={{ marginRight: '5px' }}>
                  {tag.label}{' '}
                  <Button close onClick={() => handleTagRemove(tag.id)} />
                </span>
              ))}
            </dd>
          )}
          <dt>
            <span id="file">
              <Translate contentKey="geneticsCollabApp.article.file">File</Translate>
            </span>
          </dt>
          <dd>
            {articleEntity.file ? (
              <div>
                {articleEntity.fileContentType ? (
                  <Button onClick={openFile(articleEntity.fileContentType, articleEntity.file)} replace color="primary">
                    <FontAwesomeIcon icon="pencil-alt" />{' '}
                    <span className="d-none d-md-inline">
                      <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                      {/*{articleEntity.fileContentType}, {byteSize(articleEntity.file)}*/}
                    </span>
                  </Button>
                ) : null}
              </div>
            ) : <span>No file available</span>}
          </dd>
          <dt>
            <span id="text">
              <Translate contentKey="geneticsCollabApp.article.text">Text</Translate>
            </span>
          </dt>

          <dd>{articleEntity.text ? <HighlightEntity text={articleEntity.text || ''} entities={filteredEntities || []} /> : <span>No text avaliable</span>}</dd>
          <dt>
            <Translate contentKey="geneticsCollabApp.article.namedEntities">Entities</Translate>
          </dt>
          <dd>
            {filteredEntities && filteredEntities.length > 0 ? (
              <Table striped>
                <thead>
                <tr>
                  <th>
                    <Translate contentKey="geneticsCollabApp.article.text">Text</Translate>
                  </th>
                  <th>
                    <Translate contentKey="geneticsCollabApp.tag.label">label</Translate>
                  </th>
                  <th>
                    <Translate contentKey="geneticsCollabApp.namedEntity.startChar">Start Char</Translate>
                  </th>
                  <th>
                    <Translate contentKey="geneticsCollabApp.namedEntity.endChar">End Char</Translate>
                  </th>
                </tr>
                </thead>
                <tbody>
                {filteredEntities.map((entity, index) => (
                  <tr key={entity.id}>
                    <td>{entity.text}</td>
                    <td>
                      {entity.tag ? (
                        <Link key={entity.tag.id} to={`/tag/${entity.tag.id}`}>
                          <span className="badge badge-info">
                            {entity.tag.label}
                          </span>
                        </Link>
                      ) : (
                        <span>No Tags</span>
                      )}
                    </td>
                    <td>{entity.startChar}</td>
                    <td>{entity.endChar}</td>
                  </tr>
                ))}
                </tbody>
              </Table>
            ) : (
              <span>No entities available</span>
            )}
          </dd>
          {/*<dt>*/}
          {/*  <Translate contentKey="geneticsCollabApp.article.namedEntities">Named Entities</Translate>*/}
          {/*</dt>*/}
          <dd>
            {filteredEntities.map((entity, i) => (
              <span key={entity.id}>
                <a>{entity.text}</a>
                {filteredEntities && i === filteredEntities.length - 1 ? '' : ', '}
              </span>
            ))}
          </dd>
          <dt>
            <span id="interactionsImage">
              {articleEntity.interactionsImage ?
                <Translate contentKey="geneticsCollabApp.article.interactionsImage">Interactions Image</Translate> : ''}
            </span>
          </dt>
          <dd>
            {articleEntity.interactionsImage ? (
              <div>
                {articleEntity.interactionsImageContentType ? (
                  <a onClick={openFile(articleEntity.interactionsImageContentType, articleEntity.interactionsImage)}>
                    <img
                      src={`data:${articleEntity.interactionsImageContentType};base64,${articleEntity.interactionsImage}`}
                      //style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : <span>No Interactions image available</span>}
                {/*                <span>
                  {articleEntity.interactionsImageContentType}, {byteSize(articleEntity.interactionsImage)}
                </span>*/}
              </div>
            ) : null}
          </dd>
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
