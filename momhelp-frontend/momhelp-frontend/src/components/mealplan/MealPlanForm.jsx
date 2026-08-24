import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';

const MealPlanForm = ({ initialData = null, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    userId: 1,
    mealDate: '',
    mealType: 'LUNCH',
    dishName: '',
    recipeId: null,
    ingredients: '',
    preparationTime: 30,
    servings: 4,
    notes: ''
  });

  const mealTypes = [
    { value: 'BREAKFAST', label: '🌅 Breakfast', color: 'warning' },
    { value: 'LUNCH', label: '☀️ Lunch', color: 'success' },
    { value: 'DINNER', label: '🌙 Dinner', color: 'primary' },
    { value: 'SNACK', label: '🍪 Snack', color: 'info' }
  ];

  useEffect(() => {
    if (initialData) {
      const dateStr = initialData.mealDate 
        ? new Date(initialData.mealDate).toISOString().split('T')[0]
        : '';
      setFormData({
        ...initialData,
        mealDate: dateStr
      });
    } else {
      const today = new Date().toISOString().split('T')[0];
      setFormData(prev => ({ ...prev, mealDate: today }));
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!formData.dishName.trim()) {
      alert('Please enter dish name');
      return;
    }
    if (!formData.mealDate) {
      alert('Please select a date');
      return;
    }
    const submitData = {
      ...formData,
      mealDate: new Date(formData.mealDate).toISOString().split('T')[0],
      preparationTime: Number(formData.preparationTime) || 30,
      servings: Number(formData.servings) || 4
    };

    onSubmit(submitData);
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-primary text-whiteContinue6:34 PM">
        <h5 className="mb-0">📅 {initialData ? 'Edit' : 'Add'} Meal Plan</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Meal Date *</Form.Label>
                <Form.Control
                  type="date"
                  value={formData.mealDate}
                  onChange={(e) => handleChange('mealDate', e.target.value)}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Meal Type *</Form.Label>
                <Form.Select
                  value={formData.mealType}
                  onChange={(e) => handleChange('mealType', e.target.value)}
                  required
                >
                  {mealTypes.map(type => (
                    <option key={type.value} value={type.value}>
                      {type.label}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Dish Name *</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="e.g., Vegetable Biryani, Paneer Curry"
                  value={formData.dishName}
                  onChange={(e) => handleChange('dishName', e.target.value)}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Preparation Time (minutes)</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.preparationTime}
                  onChange={(e) => handleChange('preparationTime', e.target.value)}
                  min="1"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
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

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Ingredients</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={3}
                  placeholder="List main ingredients (comma-separated)"
                  value={formData.ingredients}
                  onChange={(e) => handleChange('ingredients', e.target.value)}
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Notes</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={2}
                  placeholder="Additional notes or instructions"
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
            <Button variant="primary" type="submit">
              {initialData ? 'Update Plan' : 'Add to Calendar'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default MealPlanForm;
