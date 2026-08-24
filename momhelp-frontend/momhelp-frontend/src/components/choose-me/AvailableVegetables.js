import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Button, Alert } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import vegetableService from '../../services/vegetableService';
import LoadingSpinner from '../common/LoadingSpinner';
import ErrorMessage from '../common/ErrorMessage';

const AvailableVegetables = ({ onSelect }) => {
  const [vegetables, setVegetables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchAvailableVegetables();
  }, []);

  const fetchAvailableVegetables = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await vegetableService.getAvailableVegetables();
      setVegetables(response.data);
    } catch (err) {
      setError('Failed to load available vegetables. Please try again.');
      console.error('Error fetching vegetables:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleVegetableSelect = (vegetable) => {
    onSelect(vegetable);
  };

  if (loading) {
    return <LoadingSpinner message="Loading available vegetables..." />;
  }

  if (error) {
    return <ErrorMessage message={error} />;
  }

  if (vegetables.length === 0) {
    return (
      <Alert variant="warning" className="text-center py-4">
        <h5>No vegetables available!</h5>
        <p className="mb-3">Add some vegetables to get recipe suggestions.</p>
        <Button as={Link} to="/vegetables" variant="success">
          Add Vegetables
        </Button>
      </Alert>
    );
  }

  return (
    <div>
      <div className="text-center mb-4">
        <h5 className="text-success">Step 1: Select a Vegetable</h5>
        <p className="text-muted">
          Choose a vegetable from your available stock to see what dishes you can make
        </p>
      </div>

      <Row className="g-4">
        {vegetables.map((veg) => (
          <Col key={veg.id} lg={4} md={6}>
            <Card className="h-100 shadow-sm border-success">
              <Card.Body className="text-center">
                <div className="mb-3 text-success" style={{ fontSize: '3rem' }}>
                  {getVegetableEmoji(veg.name)}
                </div>
                <Card.Title>{veg.name}</Card.Title>
                <Card.Text className="text-muted">
                  <strong>Available:</strong> {veg.weight} {veg.unit}<br />
                  <small>Use before: {formatDate(veg.useBeforeDate)}</small>
                </Card.Text>
                <Button
                  variant="success"
                  onClick={() => handleVegetableSelect(veg)}
                  className="w-100"
                >
                  Choose This Vegetable
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

// Helper function to get emoji for vegetable
const getVegetableEmoji = (name) => {
  const nameLower = name.toLowerCase();
  
  const emojiMap = {
    'spinach': '🥬', 'palak': '🥬',
    'potato': '🥔', 'aloo': '🥔',
    'tomato': '🍅', 'tamatar': '🍅',
    'onion': '🧅', 'pyaz': '🧅',
    'carrot': '🥕', 'gajar': '🥕',
    'cabbage': '🥬', 'patta gobhi': '🥬',
    'broccoli': '🥦',
    'cauliflower': '🥦', 'phool gobhi': '🥦',
    'pea': '🫛', 'matar': '🫛',
    'corn': '🌽', 'makka': '🌽',
    'lady finger': '🫛', 'ladyfinger': '🫛', 'okra': '🫛', 'bhindi': '🫛',
    'eggplant': '🍆', 'brinjal': '🍆', 'baingan': '🍆',
    'pepper': '🫑', 'capsicum': '🫑', 'shimla mirch': '🫑',
    'cucumber': '🥒', 'kheera': '🥒',
    'pumpkin': '🎃', 'kaddu': '🎃',
    'beetroot': '🫐', 'chukandar': '🫐',
    'radish': '🥕', 'mooli': '🥕',
    'mushroom': '🍄',
    'ginger': '🫚',
    'garlic': '🧄', 'lahsun': '🧄',
    'chili': '🌶️', 'mirchi': '🌶️'
  };
  
  // Find matching emoji
  for (const [key, emoji] of Object.entries(emojiMap)) {
    if (nameLower.includes(key)) {
      return emoji;
    }
  }
  
  // Default: vegetable emoji
  return '🥗';
};
// Helper function to format date
const formatDate = (dateString) => {
  try {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-IN', { 
      day: '2-digit', 
      month: 'short', 
      year: 'numeric' 
    });
  } catch {
    return dateString;
  }
};

export default AvailableVegetables;
