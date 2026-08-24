import api from './api';

const aiRecipeService = {
  // Generate new recipe using AI
  generateRecipe: (recipeData) => {
    return api.post('/ai-recipe/generate', recipeData);
  },

  // Get recipe by ID
  getRecipeById: (id) => {
    return api.get(`/ai-recipe/${id}`);
  },

  // Get all generated recipes
  getAllRecipes: () => {
    return api.get('/ai-recipe/all');
  },

  // Get favorite recipes
  getFavoriteRecipes: () => {
    return api.get('/ai-recipe/favorites');
  },

  // Toggle favorite status
  toggleFavorite: (id) => {
    return api.put(`/ai-recipe/${id}/favorite`);
  },

  // Delete recipe
  deleteRecipe: (id) => {
    return api.delete(`/ai-recipe/${id}`);
  },

  // Get recent recipes
  getRecentRecipes: () => {
    return api.get('/ai-recipe/recent');
  }
};

export default aiRecipeService;