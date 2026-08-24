import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';

const RecipeForm = ({ initialData = null, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    userId: 1,
    recipeName: '',
    category: 'LUNCH',
    cuisineType: 'Indian',
    ingredients: '',
    instructions: '',
    preparationTime: 15,
    cookingTime: 30,
    servings: 4,
    difficultyLevel: 'MEDIUM',
    rating: 0,
    notes: '',
    imageUrl: ''
  });

  const categories = [
    'BREAKFAST',
    'LUNCH',
    'DINNER',
    'SNACK',
    'DESSERT'
  ];

  const cuisineTypes = [
    'Indian',
    'Chinese',
    'Italian',
    'Mexican',
    'Thai',
    'Japanese',
    'American',
    'Mediterranean',
    'Continental',
    'Other'
  ];

  const difficultyLevels = [
    { value: 'EASY', label: 'Easy', color: 'success' },
    { value: 'MEDIUM', label: 'Medium', color: 'warning' },
    { value: 'HARD', label: 'Hard', color: 'danger' }
  ];

  useEffect(() => {
    if (initialData) {
      setFormData(initialData);
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.recipeName.trim()) {
      alert('Please enter recipe name');
      return;
    }
    
    const submitData = {
      ...formData,
      preparationTime: Number(formData.preparationTime) || 0,
      cookingTime: Number(formData.cookingTime) || 0,
      servings: Number(formData.servings) || 1,
      rating: Number(formData.rating) || 0
    };
    
    onSubmit(submitData);
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-warning text-dark">
        <h5 className="mb-0">📖 {initialData ? 'Edit' : 'Add'} Recipe</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Recipe Name *</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="e.g., Paneer Butter Masala"
                  value={formData.recipeName}
                  onChange={(e) => handleChange('recipeName', e.target.value)}
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
                    <option key={cat} value={cat}>{cat}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Cuisine Type</Form.Label>
                <Form.Select
                  value={formData.cuisineType}
                  onChange={(e) => handleChange('cuisineType', e.target.value)}
                >
                  {cuisineTypes.map(cuisine => (
                    <option key={cuisine} value={cuisine}>{cuisine}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={4} className="mb-3">
              <Form.Group>
                <Form.Label>Prep Time (min)</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.preparationTime}
                  onChange={(e) => handleChange('preparationTime', e.target.value)}
                  min="0"
                />
              </Form.Group>
            </Col>

            <Col md={4} className="mb-3">
              <Form.Group>
                <Form.Label>Cook Time (min)</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.cookingTime}
                  onChange={(e) => handleChange('cookingTime', e.target.value)}
                  min="0"
                />
              </Form.Group>
            </Col>

            <Col md={4} className="mb-3">
              <Form.Group>
                <Form.Label>Servings</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.servings}
                  onChange={(e) => handleChange('servings', e.target.value)}
                  min="1"
                />
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

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Rating (0-5)</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.rating}
                  onChange={(e) => handleChange('rating', e.target.value)}
                  min="0"
                  max="5"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Ingredients</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={4}
                  placeholder="List ingredients (one per line or comma-separated)"
                  value={formData.ingredients}
                  onChange={(e) => handleChange('ingredients', e.target.value)}
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Instructions</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={6}
                  placeholder="Step-by-step cooking instructions"
                  value={formData.instructions}
                  onChange={(e) => handleChange('instructions', e.target.value)}
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
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

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Notes</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={2}
                  placeholder="Additional notes or tips"
                  value={formData.notes}
                  onChange={(e) => handleChange('notes', e.target.value)}
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
            <Button variant="warning" type="submit">
              {initialData ? 'Update Recipe' : 'Add Recipe'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default RecipeForm;