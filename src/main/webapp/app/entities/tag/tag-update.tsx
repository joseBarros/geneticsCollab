import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INLPModel } from 'app/shared/model/nlp-model.model';
import { getEntities as getNLpModels } from 'app/entities/nlp-model/nlp-model.reducer';
import { ITag } from 'app/shared/model/tag.model';
import { getEntity, updateEntity, createEntity, reset } from './tag.reducer';

export const TagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nLPModels = useAppSelector(state => state.nLPModel.entities);
  const tagEntity = useAppSelector(state => state.tag.entity);
  const loading = useAppSelector(state => state.tag.loading);
  const updating = useAppSelector(state => state.tag.updating);
  const updateSuccess = useAppSelector(state => state.tag.updateSuccess);

  const handleClose = () => {
    navigate('/tag' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    //dispatch(getNLpModels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...tagEntity,
      ...values,
      //nlpModel: nLPModels.find(it => it.id.toString() === values.nlpModel.toString()),
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
          ...tagEntity,
          //nlpModel: tagEntity?.nlpModel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="geneticsCollabApp.tag.home.createOrEditLabel" data-cy="TagCreateUpdateHeading">
            {isNew
              ? (<Translate contentKey="geneticsCollabApp.tag.home.createLabel">Create a Tag</Translate>)
              : (<Translate contentKey="geneticsCollabApp.tag.home.editLabel">Edit a Tag</Translate>)}
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
                  id="tag-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('geneticsCollabApp.tag.label')}
                id="tag-label"
                name="label"
                data-cy="label"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              {/*<ValidatedField*/}
              {/*  id="tag-nlpModel"*/}
              {/*  name="nlpModel"*/}
              {/*  data-cy="nlpModel"*/}
              {/*  label={translate('geneticsCollabApp.tag.nlpModel')}*/}
              {/*  type="select"*/}
              {/*>*/}
              {/*  <option value="" key="0" />*/}
              {/*  {nLPModels*/}
              {/*    ? nLPModels.map(otherEntity => (*/}
              {/*        <option value={otherEntity.id} key={otherEntity.id}>*/}
              {/*          {otherEntity.id}*/}
              {/*        </option>*/}
              {/*      ))*/}
              {/*    : null}*/}
              {/*</ValidatedField>*/}
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tag" replace color="info">
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

export default TagUpdate;
