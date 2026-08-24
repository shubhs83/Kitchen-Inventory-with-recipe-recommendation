import React from 'react';
import { Card, Badge, Row, Col, ListGroup } from 'react-bootstrap';

const PreferencesDisplay = ({ preferences }) => {
  if (!preferences) return null;

  const getBadgeVariant = (type) => {
    const variants = {
      VEGETARIAN: 'success',
      NON_VEGETARIAN: 'danger',
      VEGAN: 'info',
      MILD: 'success',
      MEDIUM: 'warning',
      HOT: 'danger',
      BEGINNER: 'info',
      INTERMEDIATE: 'warning',
      ADVANCED: 'success'
    };
    return variants[type] || 'secondary';
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-primary text-white">
        <h5 className="mb-0">👤 Your Preferences</h5>
      </Card.Header>
      <Card.Body>
        <Row>
          <Col md={6} className="mb-3">
            <Card className="bg-light border-0 h-100">
              <Card.Body>
                <h6 className="text-muted mb-3">🍽️ Dietary Preferences</h6>
                <div className="mb-2">
                  <strong>Diet Type:</strong>{' '}
                  <Badge bg={getBadgeVariant(preferences.dietaryPreference)}>
                    {preferences.dietaryPreference}
                  </Badge>
                </div>
                <div className="mb-2">
                  <strong>Spice Level:</strong>{' '}
                  <Badge bg={getBadgeVariant(preferences.spiceLevel)}>
                    {preferences.spiceLevel}
                  </Badge>
                </div>
                <div className="mb-2">
                  <strong>Cooking Skill:</strong>{' '}
                  <Badge bg={getBadgeVariant(preferences.cookingSkillLevel)}>
                    {preferences.cookingSkillLevel}
                  </Badge>
                </div>
              </Card.Body>
            </Card>
          </Col>

          <Col md={6} className="mb-3">
            <Card className="bg-light border-0 h-100">
              <Card.Body>
                <h6 className="text-muted mb-3">⚙️ Preferences</h6>
                <div className="mb-2">
                  <strong>Max Cooking Time:</strong> {preferences.maxCookingTime} mins
                </div>
                <div className="mb-2">
                  <strong>Default Servings:</strong> {preferences.servingSizePreference}
                </div>
                <div className="mb-2">
                  <strong>Language:</strong> {preferences.languagePreference}
                </div>
              </Card.Body>
            </Card>
          </Col>

          {preferences.allergies && preferences.allergies.length > 0 && (
            <Col md={6} className="mb-3">
              <Card className="bg-light border-0 h-100">
                <Card.Body>
                  <h6 className="text-muted mb-3">🚫 Allergies</h6>
                  <div className="d-flex flex-wrap gap-2">
                    {preferences.allergies.map((allergy, index) => (
                      <Badge key={index} bg="danger">
                        {allergy}
                      </Badge>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          )}

          {preferences.favoriteCuisines && preferences.favoriteCuisines.length > 0 && (
            <Col md={6} className="mb-3">
              <Card className="bg-light border-0 h-100">
                <Card.Body>
                  <h6 className="text-muted mb-3">❤️ Favorite Cuisines</h6>
                  <div className="d-flex flex-wrap gap-2">
                    {preferences.favoriteCuisines.map((cuisine, index) => (
                      <Badge key={index} bg="success">
                        {cuisine}
                      </Badge>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          )}

          {preferences.preferredMealTypes && preferences.preferredMealTypes.length > 0 && (
            <Col md={6} className="mb-3">
              <Card className="bg-light border-0 h-100">
                <Card.Body>
                  <h6 className="text-muted mb-3">🍴 Preferred Meals</h6>
                  <div className="d-flex flex-wrap gap-2">
                    {preferences.preferredMealTypes.map((meal, index) => (
                      <Badge key={index} bg="info">
                        {meal}
                      </Badge>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          )}

          {preferences.avoidIngredients && preferences.avoidIngredients.length > 0 && (
            <Col md={6} className="mb-3">
              <Card className="bg-light border-0 h-100">
                <Card.Body>
                  <h6 className="text-muted mb-3">⛔ Avoid Ingredients</h6>
                  <div className="d-flex flex-wrap gap-2">
                    {preferences.avoidIngredients.map((ingredient, index) => (
                      <Badge key={index} bg="warning" text="dark">
                        {ingredient}
                      </Badge>
                    ))}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          )}
        </Row>
      </Card.Body>
    </Card>
  );
};

export default PreferencesDisplay;