import React from 'react';
import { Modal, Button, Badge } from 'react-bootstrap';
import { FaHeart, FaRegHeart, FaEye, FaThumbsUp, FaPlay } from 'react-icons/fa';

const CookingTipDetail = ({ tip, show, onHide, onToggleFavorite, onMarkHelpful }) => {
  if (!tip) return null;

  const getCategoryLabel = (category) => {
    const labels = {
      BASIC_TECHNIQUES: 'Basic Techniques',
      INGREDIENT_PREP: 'Ingredient Preparation',
      COOKING_METHODS: 'Cooking Methods',
      STORAGE: 'Food Storage',
      SAFETY: 'Kitchen Safety',
      TIME_SAVING: 'Time-Saving Tips',
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

  const getYouTubeEmbedUrl = (url) => {
    if (!url) return null;
    const videoId = url.match(/(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/i);
    return videoId ? `https://www.youtube.com/embed/${videoId[1]}` : null;
  };

  const embedUrl = getYouTubeEmbedUrl(tip.videoUrl);

  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>{tip.title}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {tip.imageUrl && !embedUrl && (
          <img 
            src={tip.imageUrl} 
            alt={tip.title}
            className="w-100 mb-3 rounded"
            style={{ maxHeight: '300px', objectFit: 'cover' }}
            onError={(e) => { e.target.style.display = 'none'; }}
          />
        )}

        {embedUrl && (
          <div className="mb-3" style={{ position: 'relative', paddingBottom: '56.25%', height: 0 }}>
            <iframe
              src={embedUrl}
              title={tip.title}
              style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%' }}
              frameBorder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowFullScreen
            ></iframe>
          </div>
        )}

        {tip.videoUrl && !embedUrl && (
          <div className="mb-3">
            <Button 
              variant="danger" 
              href={tip.videoUrl} 
              target="_blank" 
              rel="noopener noreferrer"
            >
              <FaPlay /> Watch Video
            </Button>
          </div>
        )}

        <div className="d-flex justify-content-between align-items-center mb-3">
          <div className="d-flex gap-2">
            <Badge bg="info">{getCategoryLabel(tip.category)}</Badge>
            <Badge bg={getDifficultyColor(tip.difficultyLevel)}>
              {tip.difficultyLevel}
            </Badge>
          </div>
          <Button
            variant="link"
            onClick={() => onToggleFavorite(tip.id)}
            className="p-0"
          >
            {tip.isFavorite ? (
              <FaHeart className="text-danger" size={24} />
            ) : (
              <FaRegHeart className="text-muted" size={24} />
            )}
          </Button>
        </div>

        <div className="mb-3">
          <h6>💡 Tip Details</h6>
          <div style={{ whiteSpace: 'pre-wrap' }}>{tip.tipContent}</div>
        </div>

        {tip.tags && tip.tags.length > 0 && (
          <div className="mb-3">
            <h6>🏷️ Tags</h6>
            <div className="d-flex flex-wrap gap-2">
              {tip.tags.map((tag, index) => (
                <Badge key={index} bg="light" text="dark">
                  #{tag}
                </Badge>
              ))}
            </div>
          </div>
        )}

        <div className="d-flex justify-content-between align-items-center mb-3 p-3 bg-light rounded">
          <div className="text-muted">
            <FaEye /> {tip.viewCount} views
          </div>
          <div>
            <Button 
              variant="outline-success" 
              size="sm"
              onClick={() => onMarkHelpful(tip.id)}
            >
              <FaThumbsUp /> Helpful ({tip.helpfulCount})
            </Button>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default CookingTipDetail;
