import React, { useState } from 'react';
import { Container, Card, Button, Alert, Row, Col, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import LoadingSpinner from '../common/LoadingSpinner';
import api from '../../services/api';

const AutoSuggest = () => {
  const [suggestion, setSuggestion] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSuggestMenu = async () => {
    try {
      setLoading(true);
      setError(null);
      setSuggestion(null);

      const response = await api.get('/auto-suggest/random-dish');

      if (response.data.success) {
        setSuggestion(response.data);
        
        // Fetch recipe details if available
        fetchRecipeDetails(response.data.dish.id);
      } else {
        setError(response.data.message || 'Failed to get suggestion');
      }
    } catch (err) {
      console.error('Error getting suggestion:', err);
      setError('Failed to get suggestion. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const fetchRecipeDetails = async (dishId) => {
    // For now, we'll use mock recipe data
    // In a real implementation, you'd fetch from your recipe endpoint
  };

  const getCategoryIcon = (category) => {
    const icons = {
      'DESSERT': '🍰',
      'SOUTH_INDIAN': '🥘',
      'SPECIAL_MAIN': '🍛',
      'SNACKS': '🥙',
      'BEVERAGES': '☕'
    };
    return icons[category] || '✨';
  };

  const getCategoryColor = (category) => {
    const colors = {
      'DESSERT': 'danger',
      'SOUTH_INDIAN': 'warning',
      'SPECIAL_MAIN': 'success',
      'SNACKS': 'info',
      'BEVERAGES': 'secondary'
    };
    return colors[category] || 'primary';
  };

  return (
    <Container className="py-4">
      <Card className="shadow-lg">
        <Card.Header className="bg-success text-white">
          <h4 className="mb-0">✨ Auto Suggest - Surprise Me!</h4>
          <small>Get instant special dish recommendations - No selection needed!</small>
        </Card.Header>

        <Card.Body>
          {/* Info Alert */}
          <Alert variant="info" className="mb-4">
            <div className="d-flex align-items-center">
              <span className="fs-3 me-3">🎲</span>
              <div>
                <strong>Want Something Different Today?</strong>
                <p className="mb-0 small">
                  Click the button below to get a random special dish suggestion instantly!
                  <br />
                  Perfect for: Biryani, Gulab Jamun, Paneer Dishes, South Indian specials, Desserts & more!
                </p>
              </div>
            </div>
          </Alert>

          {/* Error Alert */}
          {error && (
            <Alert variant="danger" dismissible onClose={() => setError(null)}>
              {error}
            </Alert>
          )}

          {/* Main Surprise Button */}
          {!suggestion && !loading && (
            <div className="text-center py-5">
              <div className="mb-4" style={{ fontSize: '5rem' }}>🎲</div>
              <h3 className="mb-4">Ready for a Culinary Surprise?</h3>
              <Button
                variant="success"
                size="lg"
                onClick={handleSuggestMenu}
                className="px-5 py-3"
                style={{ fontSize: '1.2rem' }}
              >
                ✨ Suggest Menu
              </Button>
              <p className="text-muted mt-3">
                <small>Click to discover a special dish you can make today!</small>
              </p>
            </div>
          )}

          {/* Loading State */}
          {loading && (
            <div className="text-center py-5">
              <LoadingSpinner message="Finding the perfect dish for you..." />
              <p className="text-muted mt-3">Preparing something special...</p>
            </div>
          )}

          {/* Suggestion Result */}
          {suggestion && suggestion.dish && (
            <Card className="border-success shadow-lg">
              <Card.Header className={`bg-${getCategoryColor(suggestion.dish.category)} text-white`}>
                <div className="d-flex justify-content-between align-items-center">
                  <h4 className="mb-0">
                    {getCategoryIcon(suggestion.dish.category)} Today's Special Suggestion
                  </h4>
                  {suggestion.dish.category && (
                    <Badge bg="light" text="dark" className="px-3 py-2">
                      {suggestion.dish.category.replace('_', ' ')}
                    </Badge>
                  )}
                </div>
              </Card.Header>

              <Card.Body>
                {/* Dish Name & Description */}
                <div className="text-center mb-4 pb-4 border-bottom">
                  <h1 className="text-success mb-3 display-4">
                    {suggestion.dish.dishName}
                  </h1>
                  <p className="lead text-muted mb-4">
                    {suggestion.dish.description}
                  </p>
                  <div className="d-flex justify-content-center gap-3 flex-wrap">
                    {suggestion.dish.cuisineType && (
                      <Badge bg="info" className="px-4 py-2" style={{ fontSize: '1rem' }}>
                        🍽️ {suggestion.dish.cuisineType} Cuisine
                      </Badge>
                    )}
                    {suggestion.dish.difficultyLevel && (
                      <Badge 
                        bg={suggestion.dish.difficultyLevel === 'Easy' ? 'success' : 
                            suggestion.dish.difficultyLevel === 'Medium' ? 'warning' : 'danger'}
                        className="px-4 py-2"
                        style={{ fontSize: '1rem' }}
                      >
                        ⭐ {suggestion.dish.difficultyLevel}
                      </Badge>
                    )}
                    <Badge bg="secondary" className="px-4 py-2" style={{ fontSize: '1rem' }}>
                      👨‍👩‍👧‍👦 Serves {suggestion.dish.servings || 4}
                    </Badge>
                  </div>
                </div>

                {/* Time Details */}
                <Row className="mb-4">
                  <Col md={4} className="text-center">
                    <Card className="border-0 bg-light h-100">
                      <Card.Body>
                        <div className="fs-1 mb-2">⏱️</div>
                        <h6 className="text-muted mb-2">Prep Time</h6>
                        <h3 className="text-success mb-0">
                          {suggestion.dish.prepTime || 30} min
                        </h3>
                      </Card.Body>
                    </Card>
                  </Col>
                  <Col md={4} className="text-center">
                    <Card className="border-0 bg-light h-100">
                      <Card.Body>
                        <div className="fs-1 mb-2">🔥</div>
                        <h6 className="text-muted mb-2">Cook Time</h6>
                        <h3 className="text-success mb-0">
                          {suggestion.dish.cookTime || 30} min
                        </h3>
                      </Card.Body>
                    </Card>
                  </Col>
                  <Col md={4} className="text-center">
                    <Card className="border-0 bg-light h-100">
                      <Card.Body>
                        <div className="fs-1 mb-2">⏰</div>
                        <h6 className="text-muted mb-2">Total Time</h6>
                        <h3 className="text-success mb-0">
                          {suggestion.dish.readyInMinutes || 60} min
                        </h3>
                      </Card.Body>
                    </Card>
                  </Col>
                </Row>

                {/* Recipe Placeholder */}
                <Alert variant="warning" className="mb-4">
                  <div className="d-flex align-items-center">
                    <span className="fs-3 me-3">📝</span>
                    <div>
                      <strong>Recipe Details Coming Soon!</strong>
                      <p className="mb-0 small">
                        Full recipe with ingredients and step-by-step instructions will be displayed here.
                        <br />
                        For now, search for "{suggestion.dish.dishName}" recipe online or in your recipe book!
                      </p>
                    </div>
                  </div>
                </Alert>

                {/* Action Buttons */}
                <div className="d-flex gap-3 justify-content-center pt-4 border-top">
                  <Button
                    variant="outline-success"
                    size="lg"
                    onClick={handleSuggestMenu}
                  >
                    🎲 Try Another Dish
                  </Button>
                  <Button
                    variant="success"
                    size="lg"
                    onClick={() => navigate('/')}
                  >
                    🏠 Back to Dashboard
                  </Button>
                </div>
              </Card.Body>
            </Card>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default AutoSuggest;
