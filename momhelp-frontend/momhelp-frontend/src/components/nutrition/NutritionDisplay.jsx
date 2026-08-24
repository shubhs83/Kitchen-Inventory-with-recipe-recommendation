import React, { useState, useEffect } from 'react';
import { Card, Spinner, Alert } from 'react-bootstrap';
import nutritionService from '../../services/nutritionService';
import NutritionCard from './NutritionCard';

const NutritionDisplay = ({ vegetableName, quantity = 100 }) => {
  const [nutrition, setNutrition] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (vegetableName) {
      fetchNutrition();
    }
  }, [vegetableName, quantity]);

  const fetchNutrition = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await nutritionService.getNutritionInfo(
        vegetableName,
        quantity,
        'grams'
      );

      if (response.data.success) {
        setNutrition(response.data.data);
      } else {
        setError('Failed to load nutrition information');
      }
    } catch (err) {
      console.error('Error fetching nutrition:', err);
      setError('Failed to load nutrition information');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Card>
        <Card.Body className="text-center py-4">
          <Spinner animation="border" variant="success" />
          <p className="mt-2 text-muted">Loading nutrition info...</p>
        </Card.Body>
      </Card>
    );
  }

  if (error) {
    return (
      <Alert variant="warning">
        {error}
      </Alert>
    );
  }

  if (!nutrition) {
    return null;
  }

  return <NutritionCard nutrition={nutrition} />;
};

export default NutritionDisplay;