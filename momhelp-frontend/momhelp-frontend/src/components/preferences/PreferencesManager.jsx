import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner } from 'react-bootstrap';
import UserPreferencesForm from './UserPreferencesForm';
import PreferencesDisplay from './PreferencesDisplay';
import userPreferencesService from '../../services/userPreferencesService';

const PreferencesManager = () => {
  const [preferences, setPreferences] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const userId = 1; // Default user ID

  useEffect(() => {
    loadPreferences();
  }, []);

  const loadPreferences = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await userPreferencesService.getPreferences(userId);
      if (response.data.success) {
        setPreferences(response.data.data);
        setIsEditing(false);
      }
    } catch (err) {
      console.error('Error loading preferences:', err);
      // Load default preferences if none exist
      try {
        const defaultResponse = await userPreferencesService.getDefaultPreferences();
        if (defaultResponse.data.success) {
          setPreferences(defaultResponse.data.data);
          setIsEditing(true); // Show form to save preferences
        }
      } catch (defaultErr) {
        setError('Failed to load preferences');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleSavePreferences = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      setSuccess(null);

      let response;
      if (preferences && preferences.id) {
        // Update existing preferences
        response = await userPreferencesService.updatePreferences(userId, formData);
      } else {
        // Save new preferences
        response = await userPreferencesService.savePreferences(formData);
      }

      if (response.data.success) {
        setPreferences(response.data.data);
        setSuccess(response.data.message);
        setIsEditing(false);
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error saving preferences:', err);
      setError(err.response?.data?.message || 'Failed to save preferences');
    } finally {
      setLoading(false);
    }
  };

  const handleEditClick = () => {
    setIsEditing(true);
    setError(null);
    setSuccess(null);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    setError(null);
    setSuccess(null);
  };

  const handleResetToDefaults = async () => {
    if (window.confirm('Are you sure you want to reset to default preferences?')) {
      try {
        setLoading(true);
        const response = await userPreferencesService.getDefaultPreferences();
        if (response.data.success) {
          setPreferences(response.data.data);
          setIsEditing(true);
          setSuccess('Preferences reset to defaults. Click "Save" to apply.');
        }
      } catch (err) {
        setError('Failed to reset preferences');
      } finally {
        setLoading(false);
      }
    }
  };

  if (loading && !preferences) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="primary" />
        <p className="mt-2">Loading preferences...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <h2>⚙️ User Preferences</h2>
          <p className="text-muted">
            Customize your cooking experience by setting your dietary preferences, 
            skill level, and favorite cuisines.
          </p>
        </Col>
      </Row>

      {error && (
        <Alert variant="danger" dismissible onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert variant="success" dismissible onClose={() => setSuccess(null)}>
          {success}
        </Alert>
      )}

      <Row>
        <Col>
          {isEditing ? (
            <UserPreferencesForm
              initialData={preferences}
              onSubmit={handleSavePreferences}
              onCancel={handleCancelEdit}
            />
          ) : (
            <>
              <PreferencesDisplay preferences={preferences} />
              <div className="d-flex gap-2 mt-3">
                <Button variant="primary" onClick={handleEditClick}>
                  ✏️ Edit Preferences
                </Button>
                <Button variant="outline-secondary" onClick={handleResetToDefaults}>
                  🔄 Reset to Defaults
                </Button>
              </div>
            </>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default PreferencesManager;