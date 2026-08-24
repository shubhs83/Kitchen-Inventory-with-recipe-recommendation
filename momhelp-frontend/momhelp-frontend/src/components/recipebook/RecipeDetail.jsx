import React, { useState } from 'react';
import { Modal, Button, Badge, Row, Col } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaStar, FaRegStar, FaClock, FaUsers } from 'react-icons/fa';

const RecipeDetail = ({ recipe, show, onHide, onToggleFavorite, onUpdateRating }) => {
  const [newRating, setNewRating] = useState(recipe?.rating || 0);

  if (!recipe) return null;

  const getCategoryColor = (category) => {
    const colors = {
      BREAKFAST: 'warning',
      LUNCH: 'success',
      DINNER: 'primary',
      SNACK: 'info',
      DESSERT: 'danger'
    };
    return colors[category] || 'secondary';
  };

  const getDifficultyColor = (level) => {
    const colors = {
      EASY: 'success',
      MEDIUM: 'warning',
      HARD: 'danger'
    };
    return colors[level] || 'secondary';
  };

  const renderStars = (rating, interactive = false) => {
    return [...Array(5)].map((_, index) => (
      <span
        key={index}
        onClick={() => interactive && setNewRating(index + 1)}
        style={{ cursor: interactive ? 'pointer' : 'default' }}
      >
        {index < rating ? <FaStar className="text-warning" /> : <FaRegStar className="text-muted" />}
      </span>
    ));
  };

  const handleSaveRating = () => {
    onUpdateRating(recipe.id, newRating);
  };

  const totalTime = (recipe.preparationTime || 0) + (recipe.cookingTime || 0);

  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>{recipe.recipeName}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {recipe.imageUrl && (
          <img 
            src={recipe.imageUrl} 
            alt={recipe.recipeName}
            className="w-100 mb-3 rounded"
            style={{ maxHeight: '300px', objectFit: 'cover' }}
            onError={(e) => { e.target.style.display = 'none'; }}
          />
        )}

        <div className="d-flex justify-content-between align-items-center mb-3">
          <div className="d-flex gap-2">
            <Badge bg={getCategoryColor(recipe.category)}>{recipe.category}</Badge>
            <Badge bg="secondary">{recipe.cuisineType}</Badge>
            <Badge bg={getDifficultyColor(recipe.difficultyLevel)}>
              {recipe.difficultyLevel}
            </Badge>
          </div>
          <Button
            variant="link"
            onClick={() => onToggleFavorite(recipe.id)}
            className="p-0"
          >
            {recipe.isFavorite ? (
              <FaHeart className="text-danger" size={24} />
            ) : (
              <FaRegHeart className="text-muted" size={24} />
            )}
          </Button>
        </div>

        <Row className="mb-3">
          <Col xs={6}>
            <div className="text-muted">
              <FaClock /> Prep: {recipe.preparationTime} min
            </div>
            <div className="text-muted">
              <FaClock /> Cook: {recipe.cookingTime} min
            </div>
            <div className="text-muted">
              <strong>Total: {totalTime} min</strong>
            </div>
          </Col>
          <Col xs={6}>
            <div className="text-muted">
              <FaUsers /> Servings: {recipe.servings}
            </div>
            <div className="mt-2">
              <div className="mb-1">Rate this recipe:</div>
              <div className="d-flex align-items-center gap-2">
                {renderStars(newRating, true)}
                {newRating !== recipe.rating && (
                  <Button variant="link" size="sm" onClick={handleSaveRating}>
                    Save
                  </Button>
                )}
              </div>
            </div>
          </Col>
        </Row>

        {recipe.ingredients && (
          <div className="mb-3">
            <h6>🥗 Ingredients</h6>
            <div style={{ whiteSpace: 'pre-wrap' }}>{recipe.ingredients}</div>
          </div>
        )}

        {recipe.instructions && (
          <div className="mb-3">
            <h6>👨‍🍳 Instructions</h6>
            <div style={{ whiteSpace: 'pre-wrap' }}>{recipe.instructions}</div>
          </div>
        )}

        {recipe.notes && (
          <div className="mb-3">
            <h6>📝 Notes</h6>
            <div style={{ whiteSpace: 'pre-wrap' }} className="text-muted">
              {recipe.notes}
            </div>
          </div>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RecipeDetail;