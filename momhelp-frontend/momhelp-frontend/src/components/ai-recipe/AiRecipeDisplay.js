import React, { useState } from 'react';
import { Card, Button, Badge, Row, Col, Alert, ListGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import aiRecipeService from '../../services/aiRecipeService';

const AiRecipeDisplay = ({ recipe, onGenerateNew, onBack }) => {
  const [isFavorite, setIsFavorite] = useState(recipe.isFavorite);
  const [savingFavorite, setSavingFavorite] = useState(false);

  const handleToggleFavorite = async () => {
    try {
      setSavingFavorite(true);
      const response = await aiRecipeService.toggleFavorite(recipe.id);
      
      if (response.data.success) {
        setIsFavorite(response.data.data.isFavorite);
        toast.success(response.data.message);
      }
    } catch (err) {
      console.error('Error toggling favorite:', err);
      toast.error('Failed to update favorite status');
    } finally {
      setSavingFavorite(false);
    }
  };

  const formatIngredients = (ingredientsText) => {
    if (!ingredientsText) return [];
    return ingredientsText.split('\n')
      .filter(line => line.trim().length > 0)
      .map(line => line.trim());
  };

  const formatInstructions = (instructionsText) => {
    if (!instructionsText) return [];
    return instructionsText.split('\n')
      .filter(line => line.trim().length > 0)
      .map(line => line.trim());
  };

  const formatNutritionalInfo = (nutritionalText) => {
    if (!nutritionalText) return [];
    return nutritionalText.split('\n')
      .filter(line => line.trim().length > 0)
      .map(line => line.trim());
  };

  const getMealTypeIcon = (mealType) => {
    switch (mealType) {
      case 'BREAKFAST': return '🌅';
      case 'LUNCH_DINNER': return '🍽️';
      case 'DESSERT': return '🍰';
      default: return '🍴';
    }
  };

  const getLanguageIcon = (lang) => {
    switch (lang) {
      case 'EN': return '🇬🇧';
      case 'HI': return '🇮🇳';
      case 'TE': return '🇮🇳';
      default: return '🌐';
    }
  };

  return (
    <div>
      <Alert variant="success" className="mb-4">
        <div className="d-flex justify-content-between align-items-center">
          <div>
            <strong>✅ Recipe Generated Successfully!</strong>
            <br />
            <small>Your AI-powered recipe is ready</small>
          </div>
          <Button
            variant={isFavorite ? 'warning' : 'outline-warning'}
            size="sm"
            onClick={handleToggleFavorite}
            disabled={savingFavorite}
          >
            {isFavorite ? '⭐ Favorited' : '☆ Add to Favorites'}
          </Button>
        </div>
      </Alert>

      <Card className="border-success shadow-lg mb-4">
        <Card.Header className="bg-success text-white">
          <h3 className="mb-2">{recipe.recipeName}</h3>
          <div className="d-flex gap-2 flex-wrap">
            <Badge bg="light" text="dark">
              {getMealTypeIcon(recipe.mealType)} {recipe.mealType?.replace('_', '/')}
            </Badge>
            <Badge bg="light" text="dark">
              {getLanguageIcon(recipe.language)} {recipe.language}
            </Badge>
            <Badge bg="light" text="dark">
              👨‍👩‍👧‍👦 {recipe.servings} servings
            </Badge>
            <Badge bg="light" text="dark">
              🥬 {recipe.vegetablesUsed}
            </Badge>
          </div>
        </Card.Header>

        <Card.Body>
          {/* Time Information */}
          <Row className="mb-4">
            <Col md={4}>
              <Card className="text-center border-0 bg-light h-100">
                <Card.Body>
                  <div className="fs-1 mb-2">⏱️</div>
                  <h6 className="text-muted mb-1">Prep Time</h6>
                  <h4 className="text-success mb-0">{recipe.prepTime || 15} min</h4>
                </Card.Body>
              </Card>
            </Col>
            <Col md={4}>
              <Card className="text-center border-0 bg-light h-100">
                <Card.Body>
                  <div className="fs-1 mb-2">🔥</div>
                  <h6 className="text-muted mb-1">Cook Time</h6>
                  <h4 className="text-success mb-0">{recipe.cookTime || 30} min</h4>
                </Card.Body>
              </Card>
            </Col>
            <Col md={4}>
              <Card className="text-center border-0 bg-light h-100">
                <Card.Body>
                  <div className="fs-1 mb-2">⏰</div>
                  <h6 className="text-muted mb-1">Total Time</h6>
                  <h4 className="text-success mb-0">{recipe.totalTime || 45} min</h4>
                </Card.Body>
              </Card>
            </Col>
          </Row>

          {/* Ingredients and Instructions */}
          <Row className="mb-4">
            <Col md={5}>
              <Card className="h-100">
                <Card.Header className="bg-warning text-dark">
                  <h5 className="mb-0">📝 Ingredients</h5>
                </Card.Header>
                <Card.Body>
                  <ListGroup variant="flush">
                    {formatIngredients(recipe.ingredients).map((ingredient, index) => (
                      <ListGroup.Item key={index} className="px-0">
                        <span className="text-success fw-bold me-2">✓</span>
                        {ingredient.replace(/^[-•]\s*/, '')}
                      </ListGroup.Item>
                    ))}
                  </ListGroup>
                </Card.Body>
              </Card>
            </Col>

            <Col md={7}>
              <Card className="h-100">
                <Card.Header className="bg-primary text-white">
                  <h5 className="mb-0">👩‍🍳 Cooking Instructions</h5>
                </Card.Header>
                <Card.Body>
                  <div className="recipe-instructions">
                    {formatInstructions(recipe.instructions).map((step, index) => {
                      // Remove step numbers if already present
                      const cleanStep = step.replace(/^\d+\.\s*/, '');
                      return (
                        <div key={index} className="mb-3 d-flex">
                          <Badge
                            bg="primary"
                            className="me-3 flex-shrink-0"
                            style={{ width: '30px', height: '30px', paddingTop: '6px' }}
                          >
                            {index + 1}
                          </Badge>
                          <div className="flex-grow-1">{cleanStep}</div>
                        </div>
                      );
                    })}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>

          {/* Nutritional Information */}
          {recipe.nutritionalInfo && (
            <Card className="mb-4">
              <Card.Header className="bg-info text-white">
                <h5 className="mb-0">🥗 Nutritional Information (per serving)</h5>
              </Card.Header>
              <Card.Body>
                <Row>
                  {formatNutritionalInfo(recipe.nutritionalInfo).map((info, index) => (
                    <Col md={3} key={index} className="mb-2">
                      <div className="p-2 bg-light rounded text-center">
                        <small className="text-muted">{info.replace(/^[-•]\s*/, '')}</small>
                      </div>
                    </Col>
                  ))}
                </Row>
              </Card.Body>
            </Card>
          )}

          {/* Action Buttons */}
          <div className="d-flex justify-content-between pt-3 border-top">
            <Button variant="secondary" onClick={onBack}>
              🏠 Back to Dashboard
            </Button>
            <div>
              <Button
                variant="success"
                onClick={onGenerateNew}
                className="me-2"
              >
                🤖 Generate Another Recipe
              </Button>
              <Button
                variant="outline-success"
                onClick={() => window.print()}
              >
                🖨️ Print Recipe
              </Button>
            </div>
          </div>
        </Card.Body>
      </Card>
    </div>
  );
};

export default AiRecipeDisplay;
