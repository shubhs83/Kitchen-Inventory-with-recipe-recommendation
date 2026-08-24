import React from 'react';
import { Card, Badge, Button } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaStar, FaRegStar, FaClock, FaUsers } from 'react-icons/fa';

const RecipeCard = ({ recipe, onView, onEdit, onDelete, onToggleFavorite }) => {
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

  const renderStars = (rating) => {
    return [...Array(5)].map((_, index) => (
      index < rating ? <FaStar key={index} className="text-warning" /> : <FaRegStar key={index} className="text-muted" />
    ));
  };

  const totalTime = (recipe.preparationTime || 0) + (recipe.cookingTime || 0);

  return (
    <Card className="h-100 shadow-sm">
      {recipe.imageUrl && (
        <Card.Img 
          variant="top" 
          src={recipe.imageUrl} 
          style={{ height: '200px', objectFit: 'cover' }}
          onError={(e) => { e.target.style.display = 'none'; }}
        />
      )}
      <Card.Body>
        <div className="d-flex justify-content-between align-items-start mb-2">
          <h5 className="mb-0">{recipe.recipeName}</h5>
          <Button
            variant="link"
            className="p-0"
            onClick={() => onToggleFavorite(recipe.id)}
          >
            {recipe.isFavorite ? (
              <FaHeart className="text-danger" size={20} />
            ) : (
              <FaRegHeart className="text-muted" size={20} />
            )}
          </Button>
        </div>

        <div className="mb-2">{renderStars(recipe.rating)}</div>

        <div className="d-flex flex-wrap gap-2 mb-2">
          <Badge bg={getCategoryColor(recipe.category)}>
            {recipe.category}
          </Badge>
          <Badge bg="secondary">{recipe.cuisineType}</Badge>
          <Badge bg={getDifficultyColor(recipe.difficultyLevel)}>
            {recipe.difficultyLevel}
          </Badge>
        </div>

        <div className="d-flex gap-3 mb-3 text-muted small">
          <span><FaClock /> {totalTime} min</span>
          <span><FaUsers /> {recipe.servings} servings</span>
        </div>

        {recipe.ingredients && (
          <Card.Text className="text-muted small" style={{ 
            overflow: 'hidden', 
            textOverflow: 'ellipsis', 
            display: '-webkit-box', 
            WebkitLineClamp: 2, 
            WebkitBoxOrient: 'vertical' 
          }}>
            {recipe.ingredients}
          </Card.Text>
        )}

        <div className="d-flex gap-2 mt-3">
          <Button variant="primary" size="sm" onClick={() => onView(recipe)} className="flex-grow-1">
            View
          </Button>
          <Button variant="outline-primary" size="sm" onClick={() => onEdit(recipe)}>
            Edit
          </Button>
          <Button variant="outline-danger" size="sm" onClick={() => onDelete(recipe.id)}>
            Delete
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
};

export default RecipeCard;