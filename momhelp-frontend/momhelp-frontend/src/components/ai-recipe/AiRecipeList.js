import React, { useState, useEffect } from 'react';
import { Container, Card, Row, Col, Button, Badge, Spinner, Alert, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import aiRecipeService from '../../services/aiRecipeService';

const AiRecipeList = () => {
  const navigate = useNavigate();
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedRecipe, setSelectedRecipe] = useState(null);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchRecipes();
  }, []);

  const fetchRecipes = async () => {
    try {
      setLoading(true);
      const response = await aiRecipeService.getAllRecipes();
      setRecipes(response.data);
    } catch (err) {
      setError('Failed to load recipes');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleViewRecipe = (recipe) => {
    setSelectedRecipe(recipe);
    setShowModal(true);
  };

  const handleDeleteRecipe = async (id) => {
    if (window.confirm('Are you sure you want to delete this recipe?')) {
      try {
        await aiRecipeService.deleteRecipe(id);
        toast.success('Recipe deleted successfully');
        fetchRecipes();
      } catch (err) {
        toast.error('Failed to delete recipe');
      }
    }
  };

  const handleToggleFavorite = async (id) => {
    try {
      await aiRecipeService.toggleFavorite(id);
      fetchRecipes();
      toast.success('Favorite updated');
    } catch (err) {
      toast.error('Failed to update favorite');
    }
  };

  const getMealTypeIcon = (mealType) => {
    switch (mealType) {
      case 'BREAKFAST': return '🌅';
      case 'LUNCH_DINNER': return '🍽️';
      case 'DESSERT': return '🍰';
      default: return '🍴';
    }
  };

  if (loading) {
    return (
      <Container className="py-5 text-center">
        <Spinner animation="border" variant="success" />
        <p className="mt-3">Loading recipes...</p>
      </Container>
    );
  }

  return (
    <Container className="py-4">
      <ToastContainer />

      <Card className="shadow">
        <Card.Header className="bg-success text-white">
          <div className="d-flex justify-content-between align-items-center">
            <h4 className="mb-0">🤖 My AI Generated Recipes</h4>
            <Button variant="light" size="sm" onClick={() => navigate('/ai-recipe-generator')}>
              ➕ Generate New Recipe
            </Button>
          </div>
        </Card.Header>

        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}

          {recipes.length === 0 ? (
            <Alert variant="info" className="text-center py-5">
              <h5>No recipes generated yet</h5>
              <p>Start generating AI-powered recipes from your vegetables!</p>
              <Button variant="success" onClick={() => navigate('/ai-recipe-generator')}>
                Generate Your First Recipe
              </Button>
            </Alert>
          ) : (
            <Row>
              {recipes.map((recipe) => (
                <Col key={recipe.id} md={6} lg={4} className="mb-4">
                  <Card className="h-100 shadow-sm">
                    <Card.Body>
                      <div className="d-flex justify-content-between align-items-start mb-2">
                        <h5 className="mb-0">{recipe.recipeName}</h5>
                        <Button
                          variant="link"
                          size="sm"
                          className="p-0"
                          onClick={() => handleToggleFavorite(recipe.id)}
                        >
                          {recipe.isFavorite ? '⭐' : '☆'}
                        </Button>
                      </div>

                      <div className="mb-3">
                        <Badge bg="secondary" className="me-1">
                          {getMealTypeIcon(recipe.mealType)}
                        </Badge>
                        <Badge bg="info" className="me-1">
                          {recipe.servings} servings
                        </Badge>
                        <Badge bg="warning" text="dark">
                          {recipe.totalTime} min
                        </Badge>
                      </div>

                      <p className="text-muted small mb-3">
                        <strong>Vegetables:</strong> {recipe.vegetablesUsed}
                      </p>

                      <div className="d-flex gap-2">
                        <Button
                          variant="success"
                          size="sm"
                          className="flex-grow-1"
                          onClick={() => handleViewRecipe(recipe)}
                        >
                          View Recipe
                        </Button>
                        <Button
                          variant="outline-danger"
                          size="sm"
                          onClick={() => handleDeleteRecipe(recipe.id)}
                        >
                          🗑️
                        </Button>
                      </div>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          )}
        </Card.Body>
      </Card>

      {/* Recipe Detail Modal */}
      <Modal show={showModal} onHide={() => setShowModal(false)} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>{selectedRecipe?.recipeName}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {selectedRecipe && (
            <div>
              <h6>Ingredients:</h6>
              <pre style={{ whiteSpace: 'pre-wrap' }}>{selectedRecipe.ingredients}</pre>
              <h6 className="mt-3">Instructions:</h6>
              <pre style={{ whiteSpace: 'pre-wrap' }}>{selectedRecipe.instructions}</pre>
            </div>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default AiRecipeList;