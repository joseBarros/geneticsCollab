import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArticle } from 'app/shared/model/article.model';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { INamedEntity } from 'app/shared/model/named-entity.model';
import { getEntity, updateEntity, createEntity, reset } from './named-entity.reducer';

export const NamedEntityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const articles = useAppSelector(state => state.article.entities);
  const tags = useAppSelector(state => state.tag.entities);
  const namedEntityEntity = useAppSelector(state => state.namedEntity.entity);
  const loading = useAppSelector(state => state.namedEntity.loading);
  const updating = useAppSelector(state => state.namedEntity.updating);
  const updateSuccess = useAppSelector(state => state.namedEntity.updateSuccess);

  const handleClose = () => {
    navigate('/named-entity' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArticles({}));
    dispatch(getTags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.startChar !== undefined && typeof values.startChar !== 'number') {
      values.startChar = Number(values.startChar);
    }
    if (values.endChar !== undefined && typeof values.endChar !== 'number') {
      values.endChar = Number(values.endChar);
    }

    const entity = {
      ...namedEntityEntity,
      ...values,
      article: articles.find(it => it.id.toString() === values.article.toString()),
      tag: tags.find(it => it.id.toString() === values.tag.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...namedEntityEntity,
          article: namedEntityEntity?.article?.id,
          tag: namedEntityEntity?.tag?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="geneticsCollabApp.namedEntity.home.createOrEditLabel" data-cy="NamedEntityCreateUpdateHeading">
            {isNew
              ? (<Translate contentKey="geneticsCollabApp.namedEntity.home.createLabel">Create a NamedEntity</Translate>)
              : (<Translate contentKey="geneticsCollabApp.namedEntity.home.editLabel">Edit a NamedEntity</Translate>)}
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="named-entity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('geneticsCollabApp.namedEntity.text')}
                id="named-entity-text"
                name="text"
                data-cy="text"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('geneticsCollabApp.namedEntity.startChar')}
                id="named-entity-startChar"
                name="startChar"
                data-cy="startChar"
                type="text"
              />
              <ValidatedField
                label={translate('geneticsCollabApp.namedEntity.endChar')}
                id="named-entity-endChar"
                name="endChar"
                data-cy="endChar"
                type="text"
              />
              <ValidatedField
                id="named-entity-article"
                name="article"
                data-cy="article"
                label={translate('geneticsCollabApp.namedEntity.article')}
                type="select"
              >
                <option value="" key="0" />
                {articles
                  ? articles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="named-entity-tag"
                name="tag"
                data-cy="tag"
                label={translate('geneticsCollabApp.namedEntity.tag')}
                type="select"
              >
                <option value="" key="0" />
                {tags
                  ? tags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/named-entity" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NamedEntityUpdate;
