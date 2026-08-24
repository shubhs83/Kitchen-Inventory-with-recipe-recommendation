import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';

const ShoppingListForm = ({ initialData = null, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    userId: 1,
    itemName: '',
    quantity: 1,
    unit: 'kg',
    category: 'Vegetables',
    priority: 'MEDIUM',
    estimatedPrice: 0,
    notes: ''
  });

  const categories = [
    'Vegetables',
    'Fruits',
    'Dairy',
    'Grains',
    'Spices',
    'Meat',
    'Seafood',
    'Snacks',
    'Beverages',
    'Condiments',
    'Others'
  ];

  const units = [
    'kg',
    'grams',
    'liters',
    'ml',
    'pieces',
    'packets',
    'bunches',
    'dozens'
  ];

  const priorities = [
    { value: 'HIGH', label: 'High', color: 'danger' },
    { value: 'MEDIUM', label: 'Medium', color: 'warning' },
    { value: 'LOW', label: 'Low', color: 'info' }
  ];

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        quantity: initialData.quantity || 1,
        estimatedPrice: initialData.estimatedPrice || 0
      });
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (!formData.itemName.trim()) {
      alert('Please enter item name');
      return;
    }
    
    const submitData = {
      userId: formData.userId,
      itemName: formData.itemName,
      quantity: Number(formData.quantity) || 1,
      unit: formData.unit,
      category: formData.category,
      priority: formData.priority,
      estimatedPrice: Number(formData.estimatedPrice) || 0,
      notes: formData.notes
    };
    
    console.log('Submitting data:', submitData);
    onSubmit(submitData);
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-success text-white">
        <h5 className="mb-0">🛒 {initialData ? 'Edit' : 'Add'} Shopping Item</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Item Name *</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="e.g., Tomatoes, Milk, Rice"
                  value={formData.itemName}
                  onChange={(e) => handleChange('itemName', e.target.value)}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={3} className="mb-3">
              <Form.Group>
                <Form.Label>Quantity</Form.Label>
                <Form.Control
                  type="number"
                  step="0.1"
                  value={formData.quantity}
                  onChange={(e) => handleChange('quantity', e.target.value)}
                  min="0"
                />
              </Form.Group>
            </Col>

            <Col md={3} className="mb-3">
              <Form.Group>
                <Form.Label>Unit</Form.Label>
                <Form.Select
                  value={formData.unit}
                  onChange={(e) => handleChange('unit', e.target.value)}
                >
                  {units.map(unit => (
                    <option key={unit} value={unit}>{unit}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Category</Form.Label>
                <Form.Select
                  value={formData.category}
                  onChange={(e) => handleChange('category', e.target.value)}
                >
                  {categories.map(category => (
                    <option key={category} value={category}>{category}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Priority</Form.Label>
                <Form.Select
                  value={formData.priority}
                  onChange={(e) => handleChange('priority', e.target.value)}
                >
                  {priorities.map(priority => (
                    <option key={priority.value} value={priority.value}>
                      {priority.label}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>Estimated Price (₹)</Form.Label>
                <Form.Control
                  type="number"
                  step="0.01"
                  value={formData.estimatedPrice}
                  onChange={(e) => handleChange('estimatedPrice', e.target.value)}
                  min="0"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>Notes</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={2}
                  placeholder="Additional notes..."
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
            <Button variant="success" type="submit">
              {initialData ? 'Update Item' : 'Add to List'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default ShoppingListForm;