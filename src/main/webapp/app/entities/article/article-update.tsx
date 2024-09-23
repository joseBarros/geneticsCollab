import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {
  Translate,
  translate,
  ValidatedField,
  ValidatedForm,
  ValidatedBlobField,
  openFile, byteSize
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INamedEntity } from 'app/shared/model/named-entity.model';
import { getEntities as getNamedEntities } from 'app/entities/named-entity/named-entity.reducer';
import { INLPModel } from 'app/shared/model/nlp-model.model';
import { getEntities as getNLpModels } from 'app/entities/nlp-model/nlp-model.reducer';
import { IArticle } from 'app/shared/model/article.model';
import { getEntity, updateEntity, createEntity, reset } from './article.reducer';

export const ArticleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const namedEntities = useAppSelector(state => state.namedEntity.entities);
  const nLPModels = useAppSelector(state => state.nLPModel.entities);
  const articleEntity = useAppSelector(state => state.article.entity);
  const loading = useAppSelector(state => state.article.loading);
  const updating = useAppSelector(state => state.article.updating);
  const updateSuccess = useAppSelector(state => state.article.updateSuccess);

  const handleClose = () => {
    navigate('/article' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNamedEntities({}));
    dispatch(getNLpModels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...articleEntity,
      ...values,
      entities: mapIdList(values.entities),
      model: nLPModels.find(it => it.id.toString() === values.model.toString()),
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
          ...articleEntity,
          entities: articleEntity?.entities?.map(e => e.id.toString()),
          model: articleEntity?.model?.id,
          file: articleEntity?.file,
          text: articleEntity?.text,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="geneticsCollabApp.article.home.createOrEditLabel" data-cy="ArticleCreateUpdateHeading">
            <Translate contentKey="geneticsCollabApp.article.home.createOrEditLabel">Create or edit a Article</Translate>
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
                  id="article-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('geneticsCollabApp.article.title')}
                id="article-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('geneticsCollabApp.article.file')}
                id="article-file"
                name="file"
                data-cy="file"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('geneticsCollabApp.article.text')}
                id="article-text"
                name="text"
                data-cy="text"
                type="textarea"
                defaultValue={articleEntity?.text}
                style={{height: "250px"}}
              />
              {/* <ValidatedField
                label={translate('geneticsCollabApp.article.summary')}
                id="article-summary"
                name="summary"
                data-cy="summary"
                type="text"
              />*/}
              {/* <ValidatedField*/}
              {/*  label={translate('geneticsCollabApp.article.entities')}*/}
              {/*  id="article-entities"*/}
              {/*  data-cy="entities"*/}
              {/*  type="select"*/}
              {/*  multiple*/}
              {/*  name="entities"*/}
              {/*>*/}
              {/*  <option value="" key="0" />*/}
              {/*  {namedEntities*/}
              {/*    ? namedEntities.map(otherEntity => (*/}
              {/*        <option value={otherEntity.id} key={otherEntity.id}>*/}
              {/*          {otherEntity.text}*/}
              {/*        </option>*/}
              {/*      ))*/}
              {/*    : null}*/}
              {/*</ValidatedField>*/}
              <ValidatedField
                id="article-model"
                name="model"
                data-cy="model"
                label={translate('geneticsCollabApp.article.model')}
                type="select"
              >
                <option value="" key="0" />
                {nLPModels
                  ? nLPModels.map(otherEntity => (
                    <option value={otherEntity.id} key={otherEntity.id}>
                      {otherEntity.name}
                    </option>
                  ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/article" replace color="info">
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

export default ArticleUpdate;
