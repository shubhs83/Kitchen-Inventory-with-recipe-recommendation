import React from 'react';
import { Card, Badge, Button, Form } from 'react-bootstrap';
import { FaEdit, FaTrash, FaClock, FaUsers } from 'react-icons/fa';

const MealPlanCard = ({ mealPlan, onEdit, onDelete, onTogglePrepared }) => {
  const getMealTypeColor = (type) => {
    const colors = {
      BREAKFAST: 'warning',
      LUNCH: 'success',
      DINNER: 'primary',
      SNACK: 'info'
    };
    return colors[type] || 'secondary';
  };

  const getMealTypeIcon = (type) => {
    const icons = {
      BREAKFAST: '🌅',
      LUNCH: '☀️',
      DINNER: '🌙',
      SNACK: '🍪'
    };
    return icons[type] || '🍽️';
  };

  return (
    <Card 
      className={`mb-3 shadow-sm ${mealPlan.isPrepared ? 'bg-light' : ''}`}
      style={{ opacity: mealPlan.isPrepared ? 0.7 : 1 }}
    >
      <Card.Body>
        <div className="d-flex justify-content-between align-items-start">
          <div className="flex-grow-1">
            <div className="d-flex align-items-center gap-2 mb-2">
              <Form.Check
                type="checkbox"
                checked={mealPlan.isPrepared}
                onChange={() => onTogglePrepared(mealPlan.id, mealPlan.isPrepared)}
                style={{ transform: 'scale(1.2)' }}
              />
              <h5 className={`mb-0 ${mealPlan.isPrepared ? 'text-decoration-line-through' : ''}`}>
                {mealPlan.dishName}
              </h5>
            </div>

            <div className="d-flex flex-wrap gap-2 mb-2">
              <Badge bg={getMealTypeColor(mealPlan.mealType)}>
                {getMealTypeIcon(mealPlan.mealType)} {mealPlan.mealType}
              </Badge>
              {mealPlan.preparationTime && (
                <Badge bg="secondary">
                  <FaClock /> {mealPlan.preparationTime} min
                </Badge>
              )}
              {mealPlan.servings && (
                <Badge bg="info">
                  <FaUsers /> {mealPlan.servings} servings
                </Badge>
              )}
            </div>

            {mealPlan.ingredients && (
              <p className="mb-1 text-muted small">
                <strong>Ingredients:</strong> {mealPlan.ingredients}
              </p>
            )}

            {mealPlan.notes && (
              <p className="mb-0 text-muted small">
                <em>{mealPlan.notes}</em>
              </p>
            )}
          </div>

          <div className="d-flex gap-2">
            <Button
              variant="outline-primary"
              size="sm"
              onClick={() => onEdit(mealPlan)}
              disabled={mealPlan.isPrepared}
            >
              <FaEdit />
            </Button>
            <Button
              variant="outline-danger"
              size="sm"
              onClick={() => onDelete(mealPlan.id)}
            >
              <FaTrash />
            </Button>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

export default MealPlanCard;
