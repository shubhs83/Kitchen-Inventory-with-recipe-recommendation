import React, { useState, useEffect } from 'react';
import { Container, Card, Row, Col, Badge, Button, Alert } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import LoadingSpinner from '../common/LoadingSpinner';
import api from '../../services/api';

const SeasonalRecommendations = () => {
  const [season, setSeason] = useState('');
  const [dishes, setDishes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchRecommendations();
  }, []);

  const fetchRecommendations = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await api.get('/seasonal/recommendations');
      
      if (response.data.success) {
        setSeason(response.data.season);
        setDishes(response.data.dishes);
      }
    } catch (err) {
      setError('Failed to load recommendations');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const getSeasonIcon = (season) => {
    const icons = {
      'SUMMER': '☀️',
      'RAINY': '🌧️',
      'WINTER': '❄️'
    };
    return icons[season] || '🌍';
  };

  const getSeasonColor = (season) => {
    const colors = {
      'SUMMER': 'warning',
      'RAINY': 'info',
      'WINTER': 'primary'
    };
    return colors[season] || 'secondary';
  };

  if (loading) return <LoadingSpinner message="Loading seasonal dishes..." />;

  return (
    <Container className="py-4">
      <Card className="shadow-lg">
        <Card.Header className={`bg-${getSeasonColor(season)} text-white`}>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h4 className="mb-0">
                {getSeasonIcon(season)} Seasonal Recommendations
              </h4>
              <small>Perfect dishes for {season} season</small>
            </div>
            <Button variant="light" size="sm" onClick={fetchRecommendations}>
              🔄 Refresh
            </Button>
          </div>
        </Card.Header>

        <Card.Body>
          <Alert variant="info" className="mb-4">
            <strong>Current Season: {season}</strong>
            <br />
            <small>Dishes you cooked in the last 3 days are automatically excluded from recommendations</small>
          </Alert>

          {error && <Alert variant="danger">{error}</Alert>}

          {dishes.length === 0 ? (
            <Alert variant="warning" className="text-center py-5">
              <h5>No dishes available for this season</h5>
              <p>Please add seasonal dishes to the database</p>
            </Alert>
          ) : (
            <Row className="g-4">
              {dishes.map((dish) => (
                <Col key={dish.id} lg={4} md={6}>
                  <Card className="h-100 shadow-sm">
                    <Card.Body>
                      <div className="d-flex justify-content-between align-items-start mb-3">
                        <h5 className="mb-0">{dish.dishName}</h5>
                        <Badge bg={getSeasonColor(season)}>
                          {getSeasonIcon(season)}
                        </Badge>
                      </div>
                      
                      <Card.Text className="text-muted mb-3">
                        {dish.description}
                      </Card.Text>

                      <div className="mb-3">
                        <small className="text-muted">
                          ⏱️ Prep: {dish.prepTime || 0} min | 
                          🔥 Cook: {dish.cookTime || 0} min
                        </small>
                      </div>

                      {dish.ingredients && (
                        <details className="mb-3">
                          <summary className="fw-bold" style={{cursor: 'pointer'}}>
                            📝 Ingredients
                          </summary>
                          <p className="small mt-2 text-muted">{dish.ingredients}</p>
                        </details>
                      )}

                      {dish.instructions && (
                        <details>
                          <summary className="fw-bold" style={{cursor: 'pointer'}}>
                            👩‍🍳 Instructions
                          </summary>
                          <div className="small mt-2 text-muted" style={{whiteSpace: 'pre-line'}}>
                            {dish.instructions}
                          </div>
                        </details>
                      )}
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
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

export default SeasonalRecommendations;
