import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';

const CookingTipForm = ({ initialData = null, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    userId: 1,
    title: '',
    category: 'BASIC_TECHNIQUES',
    tipContent: '',
    difficultyLevel: 'BEGINNER',
    tags: [],
    videoUrl: '',
    imageUrl: ''
  });

  const categories = [
    { value: 'BASIC_TECHNIQUES', label: 'Basic Techniques' },
    { value: 'INGREDIENT_PREP', label: 'Ingredient Preparation' },
    { value: 'COOKING_METHODS', label: 'Cooking Methods' },
    { value: 'STORAGE', label: 'Food Storage' },
    { value: 'SAFETY', label: 'Kitchen Safety' },
    { value: 'TIME_SAVING', label: 'Time-Saving Tips' },
    { value: 'HEALTHY_COOKING', label: 'Healthy Cooking' }
  ];

  const difficultyLevels = [
    { value: 'BEGINNER', label: 'Beginner', color: 'success' },
    { value: 'INTERMEDIATE', label: 'Intermediate', color: 'warning' },
    { value: 'ADVANCED', label: 'Advanced', color: 'danger' }
  ];

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleTagsChange = (e) => {
    const tags = e.target.value.split(',').map(tag => tag.trim()).filter(tag => tag);
    setFormData(prev => ({ ...prev, tags }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.title.trim()) {
      alert('Please enter tip title');
      return;
    }
    
    onSubmit(formData);
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-info text-white">
        <h5 className="mb-0">💡 {initialData ? 'Edit' : 'Add'} Cooking Tip</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Tip Title *</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="e.g., How to Perfectly Boil Eggs"
                  value={formData.title}
                  onChange={(e) => handleChange('title', e.target.value)}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Category</Form.Label>
                <Form.Select
                  value={formData.category}
                  onChange={(e) => handleChange('category', e.target.value)}
                >
                  {categories.map(cat => (
                    <option key={cat.value} value={cat.value}>{cat.label}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Difficulty Level</Form.Label>
                <Form.Select
                  value={formData.difficultyLevel}
                  onChange={(e) => handleChange('difficultyLevel', e.target.value)}
                >
                  {difficultyLevels.map(level => (
                    <option key={level.value} value={level.value}>
                      {level.label}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Tip Content *</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={6}
                  placeholder="Detailed explanation of the cooking tip..."
                  value={formData.tipContent}
                  onChange={(e) => handleChange('tipContent', e.target.value)}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Tags (comma-separated)</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="e.g., eggs, boiling, breakfast, protein"
                  value={formData.tags.join(', ')}
                  onChange={handleTagsChange}
                />
                <Form.Text className="text-muted">
                  Separate tags with commas
                </Form.Text>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Video URL (optional)</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="https://youtube.com/..."
                  value={formData.videoUrl}
                  onChange={(e) => handleChange('videoUrl', e.target.value)}
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Image URL (optional)</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="https://example.com/image.jpg"
                  value={formData.imageUrl}
                  onChange={(e) => handleChange('imageUrl', e.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>

          <div className="d-flex gap-2 justify-content-end">
            {onCancel && (
              <Button variant="secondary" onClick={onCancel} type="button">
                Cancel
              </Button>
            )}
            <Button variant="info" type="submit">
              {initialData ? 'Update Tip' : 'Add Tip'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default CookingTipForm;