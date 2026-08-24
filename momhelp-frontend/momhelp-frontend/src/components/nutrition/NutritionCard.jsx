import React from 'react';
import { Card, Row, Col, ProgressBar } from 'react-bootstrap';

const NutritionCard = ({ nutrition }) => {
  if (!nutrition) return null;

  const getNutrientColor = (type) => {
    const colors = {
      calories: 'danger',
      protein: 'success',
      carbs: 'warning',
      fat: 'info',
      fiber: 'primary'
    };
    return colors[type] || 'secondary';
  };

  const calculatePercentage = (value, max) => {
    return Math.min((value / max) * 100, 100);
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-success text-white">
        <h5 className="mb-0">🥗 Nutritional Information</h5>
        <small>{nutrition.foodName} ({nutrition.servingSize}g per serving)</small>
      </Card.Header>
      <Card.Body>
        <Row className="mb-4">
          <Col md={6}>
            <div className="text-center p-3 border rounded bg-light">
              <div className="display-4 text-danger fw-bold">
                {nutrition.calories?.toFixed(0)}
              </div>
              <div className="text-muted">Calories</div>
            </div>
          </Col>
          <Col md={6}>
            <div className="text-center p-3 border rounded bg-light">
              <div className="display-4 text-success fw-bold">
                {nutrition.protein?.toFixed(1)}g
              </div>
              <div className="text-muted">Protein</div>
            </div>
          </Col>
        </Row>

        <div className="mb-3">
          <div className="d-flex justify-content-between mb-1">
            <span className="fw-bold">Carbohydrates</span>
            <span>{nutrition.carbohydrates?.toFixed(1)}g</span>
          </div>
          <ProgressBar 
            variant="warning" 
            now={calculatePercentage(nutrition.carbohydrates, 50)} 
            label={`${nutrition.carbohydrates?.toFixed(1)}g`}
          />
        </div>

        <div className="mb-3">
          <div className="d-flex justify-content-between mb-1">
            <span className="fw-bold">Fat</span>
            <span>{nutrition.fat?.toFixed(1)}g</span>
          </div>
          <ProgressBar 
            variant="info" 
            now={calculatePercentage(nutrition.fat, 20)} 
            label={`${nutrition.fat?.toFixed(1)}g`}
          />
        </div>

        <div className="mb-3">
          <div className="d-flex justify-content-between mb-1">
            <span className="fw-bold">Fiber</span>
            <span>{nutrition.fiber?.toFixed(1)}g</span>
          </div>
          <ProgressBar 
            variant="primary" 
            now={calculatePercentage(nutrition.fiber, 25)} 
            label={`${nutrition.fiber?.toFixed(1)}g`}
          />
        </div>

        {nutrition.sugar > 0 && (
          <div className="mb-3">
            <div className="d-flex justify-content-between mb-1">
              <span className="fw-bold">Sugar</span>
              <span>{nutrition.sugar?.toFixed(1)}g</span>
            </div>
            <ProgressBar 
              variant="danger" 
              now={calculatePercentage(nutrition.sugar, 25)} 
            />
          </div>
        )}

        <div className="mt-4 pt-3 border-top">
          <Row className="text-center">
            <Col xs={4}>
              <div className="text-muted small">Sodium</div>
              <div className="fw-bold">{nutrition.sodium?.toFixed(0)}mg</div>
            </Col>
            <Col xs={4}>
              <div className="text-muted small">Vitamin C</div>
              <div className="fw-bold">{nutrition.vitaminC?.toFixed(0)}mg</div>
            </Col>
            <Col xs={4}>
              <div className="text-muted small">Iron</div>
              <div className="fw-bold">{nutrition.iron?.toFixed(1)}mg</div>
            </Col>
          </Row>
        </div>
      </Card.Body>
    </Card>
  );
};

export default NutritionCard;