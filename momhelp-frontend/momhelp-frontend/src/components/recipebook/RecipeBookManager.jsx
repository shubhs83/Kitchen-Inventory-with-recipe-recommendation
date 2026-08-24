import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner, Tabs, Tab, Form, InputGroup } from 'react-bootstrap';
import { FaSearch } from 'react-icons/fa';
import RecipeForm from './RecipeForm';
import RecipeCard from './RecipeCard';
import RecipeDetail from './RecipeDetail';
import recipeBookService from '../../services/recipeBookService';

const RecipeBookManager = () => {
  const [recipes, setRecipes] = useState([]);
  const [filteredRecipes, setFilteredRecipes] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [showDetail, setShowDetail] = useState(false);
  const [editingRecipe, setEditingRecipe] = useState(null);
  const [viewingRecipe, setViewingRecipe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [activeTab, setActiveTab] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');

  const userId = 1;

  useEffect(() => {
    loadRecipes();
  }, []);

  useEffect(() => {
    filterRecipes();
  }, [recipes, activeTab, searchQuery]);

  const loadRecipes = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await recipeBookService.getAllRecipes(userId);
      if (response.data.success) {
        setRecipes(response.data.data);
      }
    } catch (err) {
      console.error('Error loading recipes:', err);
      setRecipes([]);
    } finally {
      setLoading(false);
    }
  };

  const filterRecipes = () => {
    let filtered = [...recipes];

    if (searchQuery) {
      filtered = filtered.filter(recipe =>
        recipe.recipeName.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }

    switch (activeTab) {
      case 'favorites':
        filtered = filtered.filter(r => r.isFavorite);
        break;
      case 'breakfast':
        filtered = filtered.filter(r => r.category === 'BREAKFAST');
        break;
      case 'lunch':
        filtered = filtered.filter(r => r.category === 'LUNCH');
        break;
      case 'dinner':
        filtered = filtered.filter(r => r.category === 'DINNER');
        break;
      case 'snack':
        filtered = filtered.filter(r => r.category === 'SNACK');
        break;
      case 'dessert':
        filtered = filtered.filter(r => r.category === 'DESSERT');
        break;
      default:
        break;
    }

    setFilteredRecipes(filtered);
  };

  const handleAddRecipe = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await recipeBookService.addRecipe(formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        await loadRecipes();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error adding recipe:', err);
      setError(err.response?.data?.message || 'Failed to add recipe');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateRecipe = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await recipeBookService.updateRecipe(editingRecipe.id, formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        setEditingRecipe(null);
        await loadRecipes();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error updating recipe:', err);
      setError(err.response?.data?.message || 'Failed to update recipe');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (formData) => {
    if (editingRecipe) {
      handleUpdateRecipe(formData);
    } else {
      handleAddRecipe(formData);
    }
  };

  const handleView = (recipe) => {
    setViewingRecipe(recipe);
    setShowDetail(true);
  };

  const handleEdit = (recipe) => {
    setEditingRecipe(recipe);
    setShowForm(true);
    setError(null);
    setSuccess(null);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this recipe?')) {
      try {
        setLoading(true);
        setError(null);
        const response = await recipeBookService.deleteRecipe(id);
        if (response.data.success) {
          setSuccess(response.data.message);
          await loadRecipes();
          setTimeout(() => setSuccess(null), 3000);
        }
      } catch (err) {
        console.error('Error deleting recipe:', err);
        setError(err.response?.data?.message || 'Failed to delete recipe');
      } finally {
        setLoading(false);
      }
    }
  };

  const handleToggleFavorite = async (id) => {
    try {
      setLoading(true);
      setError(null);
      const response = await recipeBookService.toggleFavorite(id);
      if (response.data.success) {
        await loadRecipes();
        if (viewingRecipe && viewingRecipe.id === id) {
          setViewingRecipe(response.data.data);
        }
      }
    } catch (err) {
      console.error('Error toggling favorite:', err);
      setError('Failed to update favorite status');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateRating = async (id, rating) => {
    try {
      setLoading(true);
      setError(null);
      const response = await recipeBookService.updateRating(id, rating);
      if (response.data.success) {
        await loadRecipes();
        if (viewingRecipe && viewingRecipe.id === id) {
          setViewingRecipe(response.data.data);
        }
      }
    } catch (err) {
      console.error('Error updating rating:', err);
      setError('Failed to update rating');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingRecipe(null);
    setError(null);
    setSuccess(null);
  };

  const favoriteCount = recipes.filter(r => r.isFavorite).length;

  if (loading && recipes.length === 0) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="warning" />
        <p className="mt-2">Loading recipe book...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2>📖 Recipe Book</h2>
              <p className="text-muted">
                Save and organize your favorite recipes
              </p>
            </div>
            <Button 
              variant="warning"
              onClick={() => {
                setShowForm(!showForm);
                setEditingRecipe(null);
              }}
            >
              {showForm ? '📋 View Recipes' : '➕ Add Recipe'}
            </Button>
          </div>
        </Col>
      </Row>

      {error && (
        <Alert variant="danger" dismissible onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert variant="success" dismissible onClose={() => setSuccess(null)}>
          {success}
        </Alert>
      )}

      {showForm ? (
        <RecipeForm
          initialData={editingRecipe}
          onSubmit={handleSubmit}
          onCancel={handleCancel}
        />
      ) : (
        <>
          <Row className="mb-3">
            <Col md={6}>
              <InputGroup>
                <InputGroup.Text>
                  <FaSearch />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder="Search recipes..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
              </InputGroup>
            </Col>
          </Row>

          <Tabs
            activeKey={activeTab}
            onSelect={(k) => setActiveTab(k)}
            className="mb-3"Continue >
        <Tab eventKey="all" title={`All (${recipes.length})`} />
        <Tab eventKey="favorites" title={`❤️ Favorites (${favoriteCount})`} />
        <Tab eventKey="breakfast" title="🌅 Breakfast" />
        <Tab eventKey="lunch" title="☀️ Lunch" />
        <Tab eventKey="dinner" title="🌙 Dinner" />
        <Tab eventKey="snack" title="🍪 Snack" />
        <Tab eventKey="dessert" title="🍰 Dessert" />
      </Tabs>

      {filteredRecipes.length === 0 ? (
        <p className="text-center text-muted">No recipes found</p>
      ) : (
        <Row>
          {filteredRecipes.map(recipe => (
            <Col key={recipe.id} md={6} lg={4} className="mb-4">
              <RecipeCard
                recipe={recipe}
                onView={handleView}
                onEdit={handleEdit}
                onDelete={handleDelete}
                onToggleFavorite={handleToggleFavorite}
              />
            </Col>
          ))}
        </Row>
      )}
    </>
  )}

  <RecipeDetail
    recipe={viewingRecipe}
    show={showDetail}
    onHide={() => setShowDetail(false)}
    onToggleFavorite={handleToggleFavorite}
    onUpdateRating={handleUpdateRating}
  />
</Container>
);
};
export default RecipeBookManager;