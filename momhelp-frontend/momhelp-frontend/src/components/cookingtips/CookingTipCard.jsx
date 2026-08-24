import React from 'react';
import { Card, Badge, Button } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaEye, FaThumbsUp } from 'react-icons/fa';

const CookingTipCard = ({ tip, onView, onEdit, onDelete, onToggleFavorite }) => {
  const getCategoryLabel = (category) => {
    const labels = {
      BASIC_TECHNIQUES: 'Basic Techniques',
      INGREDIENT_PREP: 'Ingredient Prep',
      COOKING_METHODS: 'Cooking Methods',
      STORAGE: 'Storage',
      SAFETY: 'Kitchen Safety',
      TIME_SAVING: 'Time-Saving',
      HEALTHY_COOKING: 'Healthy Cooking'
    };
    return labels[category] || category;
  };

  const getDifficultyColor = (level) => {
    const colors = {
      BEGINNER: 'success',
      INTERMEDIATE: 'warning',
      ADVANCED: 'danger'
    };
    return colors[level] || 'secondary';
  };

  return (
    <Card className="h-100 shadow-sm">
      {tip.imageUrl && (
        <Card.Img 
          variant="top" 
          src={tip.imageUrl} 
          style={{ height: '150px', objectFit: 'cover' }}
          onError={(e) => { e.target.style.display = 'none'; }}
        />
      )}
      <Card.Body>
        <div className="d-flex justify-content-between align-items-start mb-2">
          <h5 className="mb-0">{tip.title}</h5>
          <Button
            variant="link"
            className="p-0"
            onClick={() => onToggleFavorite(tip.id)}
          >
            {tip.isFavorite ? (
              <FaHeart className="text-danger" size={20} />
            ) : (
              <FaRegHeart className="text-muted" size={20} />
            )}
          </Button>
        </div>

        <div className="d-flex flex-wrap gap-2 mb-3">
          <Badge bg="info">{getCategoryLabel(tip.category)}</Badge>
          <Badge bg={getDifficultyColor(tip.difficultyLevel)}>
            {tip.difficultyLevel}
          </Badge>
        </div>

        {tip.tipContent && (
          <Card.Text className="text-muted" style={{ 
            overflow: 'hidden', 
            textOverflow: 'ellipsis', 
            display: '-webkit-box', 
            WebkitLineClamp: 3, 
            WebkitBoxOrient: 'vertical' 
          }}>
            {tip.tipContent}
          </Card.Text>
        )}

        {tip.tags && tip.tags.length > 0 && (
          <div className="d-flex flex-wrap gap-1 mb-3">
            {tip.tags.slice(0, 3).map((tag, index) => (
              <Badge key={index} bg="light" text="dark" className="small">
                #{tag}
              </Badge>
            ))}
          </div>
        )}

        <div className="d-flex justify-content-between align-items-center mb-3 text-muted small">
          <span><FaEye /> {tip.viewCount} views</span>
          <span><FaThumbsUp /> {tip.helpfulCount} helpful</span>
        </div>

        <div className="d-flex gap-2">
          <Button variant="info" size="sm" onClick={() => onView(tip)} className="flex-grow-1">
            View
          </Button>
          <Button variant="outline-info" size="sm" onClick={() => onEdit(tip)}>
            Edit
          </Button>
          <Button variant="outline-danger" size="sm" onClick={() => onDelete(tip.id)}>
            Delete
          </Button>
        </div>
      </Card.Body>
    </Card>
  );
};

export default CookingTipCard;