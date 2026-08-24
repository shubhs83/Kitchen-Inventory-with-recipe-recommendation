import React, { useState } from 'react';
import { Container, Card, Form, Button, Row, Col, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import nutritionService from '../../services/nutritionService';
import NutritionCard from './NutritionCard';

const NutritionCalculator = () => {
  const navigate = useNavigate();
  const [foodItem, setFoodItem] = useState('');
  const [quantity, setQuantity] = useState(100);
  const [unit, setUnit] = useState('grams');
  const [nutrition, setNutrition] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleCalculate = async (e) => {
    e.preventDefault();
    
    if (!foodItem.trim()) {
      toast.warning('Please enter a food item');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      
      const response = await nutritionService.getNutritionInfo(
        foodItem,
        quantity,
        unit
      );

      if (response.data.success) {
        setNutrition(response.data.data);
        toast.success('Nutrition information loaded!');
      } else {
        setError(response.data.message || 'Failed to get nutrition info');
        toast.error('Failed to get nutrition info');
      }
    } catch (err) {
      console.error('Error:', err);
      const errorMsg = err.response?.data?.message || 'Failed to calculate nutrition';
      setError(errorMsg);
      toast.error(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setFoodItem('');
    setQuantity(100);
    setUnit('grams');
    setNutrition(null);
    setError(null);
  };

  const commonVegetables = [
    'Potato', 'Tomato', 'Onion', 'Spinach', 'Carrot',
    'Cabbage', 'Cauliflower', 'Broccoli', 'Pea', 'Corn',
    'Cucumber', 'Eggplant', 'Bell Pepper', 'Okra', 'Pumpkin'
  ];

  return (
    <Container className="py-4">
      <ToastContainer />

      <Card className="shadow-lg">
        <Card.Header className="bg-success text-white">
          <h4 className="mb-0">🥗 Nutrition Calculator</h4>
          <small>Get nutritional information for any food item</small>
        </Card.Header>

        <Card.Body>
          <Alert variant="info" className="mb-4">
            <div className="d-flex align-items-center">
              <span className="fs-3 me-3">💡</span>
              <div>
                <strong>How it works:</strong> Enter a food item name and quantity 
                to see detailed nutritional information including calories, protein, carbs, and more.
              </div>
            </div>
          </Alert>

          {error && (
            <Alert variant="danger" dismissible onClose={() => setError(null)}>
              {error}
            </Alert>
          )}

          <Form onSubmit={handleCalculate}>
            <Row>
              <Col md={6} className="mb-3">
                <Form.Group>
                  <Form.Label className="fw-bold">
                    Food Item <span className="text-danger">*</span>
                  </Form.Label>
                  <Form.Control
                    type="text"
                    value={foodItem}
                    onChange={(e) => setFoodItem(e.target.value)}
                    placeholder="Enter vegetable or food name"
                    list="common-vegetables"
                  />
                  <datalist id="common-vegetables">
                    {commonVegetables.map((veg, index) => (
                      <option key={index} value={veg} />
                    ))}
                  </datalist>
                </Form.Group>
              </Col>

              <Col md={3} className="mb-3">
                <Form.Group>
                  <Form.Label className="fw-bold">
                    Quantity <span className="text-danger">*</span>
                  </Form.Label>
                  <Form.Control
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(e.target.value)}
                    min="1"
                  />
                </Form.Group>
              </Col>

              <Col md={3} className="mb-3">
                <Form.Group>
                  <Form.Label className="fw-bold">Unit</Form.Label>
                  <Form.Select
                    value={unit}
                    onChange={(e) => setUnit(e.target.value)}
                  >
                    <option value="grams">Grams</option>
                    <option value="kg">Kg</option>
                    <option value="piece">Piece</option>
                    <option value="cup">Cup</option>
                  </Form.Select>
                </Form.Group>
              </Col>
            </Row>

            <div className="d-flex gap-2 justify-content-center mt-3">
              <Button variant="secondary" onClick={handleReset}>
                🔄 Reset
              </Button>
              <Button
                variant="success"
                type="submit"
                disabled={loading}
                className="px-4"
              >
                {loading ? 'Calculating...' : '🔍 Calculate Nutrition'}
              </Button>
            </div>
          </Form>

          {nutrition && (
            <div className="mt-4">
              <NutritionCard nutrition={nutrition} />
            </div>
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

export default NutritionCalculator;