import React, { useState } from 'react';
import {
  Container,
  Card,
  Button,
  Alert,
  Badge,
  ListGroup,
  Spinner,
  Row,
  Col,
  Form
} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import ImageUploader from './ImageUploader';
import imageDetectionService from '../../services/imageDetectionService';

const VegetableImageDetector = () => {
  const navigate = useNavigate();
  const [selectedImage, setSelectedImage] = useState(null);
  const [detecting, setDetecting] = useState(false);
  const [detectedVegetables, setDetectedVegetables] = useState([]);
  const [error, setError] = useState(null);

  const handleImageSelected = (file) => {
    setSelectedImage(file);
    setDetectedVegetables([]);
    setError(null);
  };

  const handleDetectVegetables = async () => {
    if (!selectedImage) {
      toast.warning('Please select an image first');
      return;
    }

    try {
      setDetecting(true);
      setError(null);

      const response = await imageDetectionService.detectVegetables(selectedImage);

      if (response.data.success) {
        setDetectedVegetables(response.data.detectedVegetables);

        if (response.data.detectedVegetables.length === 0) {
          toast.warning('No vegetables detected in the image. Please try another image.');
        } else {
          toast.success(`Detected ${response.data.count} vegetable(s)!`);
        }
      } else {
        setError(response.data.message || 'Failed to detect vegetables');
        toast.error('Detection failed');
      }
    } catch (err) {
      const errorMsg =
        err.response?.data?.message || 'Failed to detect vegetables. Please try again.';
      setError(errorMsg);
      toast.error(errorMsg);
    } finally {
      setDetecting(false);
    }
  };

  const handleReset = () => {
    setSelectedImage(null);
    setDetectedVegetables([]);
    setError(null);
  };

  const handleAddToInventory = (vegetableName) => {
    navigate('/vegetables', { state: { vegetableName } });
  };

  const handleGenerateRecipe = () => {
    const vegetables = detectedVegetables.map(v => v.name);
    navigate('/ai-recipe-generator', { state: { vegetables } });
  };

  return (
    <Container className="py-4">
      <ToastContainer position="top-right" autoClose={3000} />

      <Card className="shadow-lg">
        <Card.Header className="bg-primary text-white">
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h4 className="mb-0">📸 AI Vegetable Detection</h4>
              <small>Upload image to automatically detect vegetables</small>
            </div>
            <Badge bg="light" text="dark">
              ✨ Powered by AI
            </Badge>
          </div>
        </Card.Header>

        <Card.Body>
          <Alert variant="info">
            <strong>How it works:</strong> Upload a clear photo and AI will identify vegetables.
          </Alert>

          {error && (
            <Alert variant="danger" dismissible onClose={() => setError(null)}>
              {error}
            </Alert>
          )}

          <ImageUploader
            onImageSelected={handleImageSelected}
            loading={detecting}
          />

          {selectedImage && !detecting && detectedVegetables.length === 0 && (
            <div className="d-flex gap-2 mt-4 justify-content-center">
              <Button variant="secondary" onClick={handleReset}>
                🔄 Reset
              </Button>
              <Button variant="primary" onClick={handleDetectVegetables}>
                🔍 Detect Vegetables
              </Button>
            </div>
          )}

          {detecting && (
            <div className="text-center py-5">
              <Spinner animation="border" variant="primary" />
              <p className="mt-3">AI is analyzing your image...</p>
            </div>
          )}

          {/* ===== UPDATED DETECTION RESULTS WITH MANUAL EDIT ===== */}
          {detectedVegetables.length > 0 && (
            <Card className="mt-4 border-success">
              <Card.Header className="bg-success text-white">
                <div className="d-flex justify-content-between align-items-center">
                  <h5 className="mb-0">
                    ✅ Detected Vegetables ({detectedVegetables.length})
                  </h5>
                  <small>Click name to edit if incorrect</small>
                </div>
              </Card.Header>

              <Card.Body>
                <ListGroup>
                  {detectedVegetables.map((veg, index) => (
                    <VegetableDetectionItem
                      key={index}
                      vegetable={veg}
                      onNameChange={(newName) => {
                        const updated = [...detectedVegetables];
                        updated[index] = { ...veg, name: newName };
                        setDetectedVegetables(updated);
                      }}
                      onAdd={() => handleAddToInventory(veg.name)}
                    />
                  ))}
                </ListGroup>

                <div className="mt-4 pt-3 border-top">
                  <Row>
                    <Col md={6} className="mb-2">
                      <Button
                        variant="success"
                        className="w-100"
                        onClick={handleGenerateRecipe}
                      >
                        🤖 Create AI Recipe
                      </Button>
                    </Col>
                    <Col md={6} className="mb-2">
                      <Button
                        variant="outline-primary"
                        className="w-100"
                        onClick={handleReset}
                      >
                        📸 Upload New Image
                      </Button>
                    </Col>
                  </Row>
                </div>
              </Card.Body>
            </Card>
          )}

          <div className="mt-4 text-center">
            <Button variant="outline-secondary" onClick={() => navigate('/')}>
              ← Back to Dashboard
            </Button>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
};

/* ===== NEW COMPONENT ===== */
const VegetableDetectionItem = ({ vegetable, onNameChange, onAdd }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [editedName, setEditedName] = useState(vegetable.name);

  const handleSave = () => {
    if (editedName.trim()) {
      onNameChange(editedName.trim());
      setIsEditing(false);
    }
  };

  const handleCancel = () => {
    setEditedName(vegetable.name);
    setIsEditing(false);
  };

  return (
    <ListGroup.Item className="d-flex justify-content-between align-items-center">
      <div className="flex-grow-1">
        {isEditing ? (
          <div className="d-flex gap-2">
            <Form.Control
              value={editedName}
              onChange={(e) => setEditedName(e.target.value)}
              autoFocus
            />
            <Button size="sm" variant="success" onClick={handleSave}>✓</Button>
            <Button size="sm" variant="secondary" onClick={handleCancel}>✕</Button>
          </div>
        ) : (
          <div>
            <h6
              className="mb-1 text-capitalize"
              style={{ cursor: 'pointer' }}
              onClick={() => setIsEditing(true)}
            >
              {vegetable.name} ✏️
            </h6>
            <small className="text-muted">
              Confidence: {vegetable.confidence.toFixed(1)}%
            </small>
          </div>
        )}
      </div>

      {!isEditing && (
        <div className="d-flex gap-2 align-items-center">
          <Badge bg="success">
            {vegetable.confidence.toFixed(0)}%
          </Badge>
          <Button size="sm" variant="outline-success" onClick={onAdd}>
            ➕ Add
          </Button>
        </div>
      )}
    </ListGroup.Item>
  );
};

export default VegetableImageDetector;
