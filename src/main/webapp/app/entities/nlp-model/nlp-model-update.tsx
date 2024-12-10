import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedBlobField, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import axios from 'axios';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INLPModel } from 'app/shared/model/nlp-model.model';
import { getEntity, updateEntity, createEntity, reset } from './nlp-model.reducer';

export const NLPModelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nLPModelEntity = useAppSelector(state => state.nLPModel.entity);
  const loading = useAppSelector(state => state.nLPModel.loading);
  const updating = useAppSelector(state => state.nLPModel.updating);
  const updateSuccess = useAppSelector(state => state.nLPModel.updateSuccess);

  const [file, setFile] = useState(null);

  const handleFileChange = event => {
    setFile(event.target.files[0]); // Store the selected file in the state
  };

  // Configure Axios with increased timeout
  const axiosInstance = axios.create({
    timeout: 120000, // Set the timeout to 120 seconds (2 minutes) or longer
  });

  const handleClose = () => {
    navigate('/nlp-model' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line @typescript-eslint/require-await
  const saveEntity = async values => {
    const entity = {
      ...nLPModelEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity({ entity, file }));
    } else {
      dispatch(updateEntity({ entity, file }));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...nLPModelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="geneticsCollabApp.nLPModel.home.createOrEditLabel" data-cy="NLPModelCreateUpdateHeading">
            {isNew
              ? (<Translate contentKey="geneticsCollabApp.nLPModel.home.createLabel">Create a NLP Model</Translate>)
              : (<Translate contentKey="geneticsCollabApp.nLPModel.home.editLabel">Edit a NLP Model</Translate>)}
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
                  id="nlp-model-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('geneticsCollabApp.nLPModel.name')}
                id="nlp-model-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('geneticsCollabApp.nLPModel.framework')}
                id="nlp-model-framework"
                name="framework"
                data-cy="framework"
                type="text"
              />
              <ValidatedBlobField
                label={translate('geneticsCollabApp.nLPModel.file')}
                id="nlp-model-file"
                name="file"
                data-cy="file"
                openActionLabel={translate('entity.action.open')}
                isImage={false}
                accept=".zip,.tar,.tar.gz,.rar"
                onChange={handleFileChange}
              />
              <ValidatedField
                label={translate('geneticsCollabApp.nLPModel.path')}
                id="nlp-model-path"
                name="path"
                data-cy="path"
                type="text"
                readOnly
              />
              <ValidatedField
                label={translate('geneticsCollabApp.nLPModel.notes')}
                id="nlp-model-notes"
                name="notes"
                data-cy="notes"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nlp-model" replace color="info">
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

export default NLPModelUpdate;
