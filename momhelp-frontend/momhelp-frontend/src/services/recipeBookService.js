import api from './api';

const recipeBookService = {
  addRecipe: (recipe) => {
    return api.post('/recipe-book/add', recipe);
  },

  updateRecipe: (id, recipe) => {
    return api.put(`/recipe-book/${id}`, recipe);
  },

  toggleFavorite: (id) => {
    return api.patch(`/recipe-book/${id}/favorite`);
  },

  updateRating: (id, rating) => {
    return api.patch(`/recipe-book/${id}/rating`, null, { params: { rating } });
  },

  getAllRecipes: (userId) => {
    return api.get(`/recipe-book/user/${userId}`);
  },

  getRecipeById: (id) => {
    return api.get(`/recipe-book/${id}`);
  },

  getFavoriteRecipes: (userId) => {
    return api.get(`/recipe-book/user/${userId}/favorites`);
  },

  getRecipesByCategory: (userId, category) => {
    return api.get(`/recipe-book/user/${userId}/category/${category}`);
  },

  getRecipesByCuisine: (userId, cuisineType) => {
    return api.get(`/recipe-book/user/${userId}/cuisine/${cuisineType}`);
  },

  getRecipesByDifficulty: (userId, difficultyLevel) => {
    return api.get(`/recipe-book/user/${userId}/difficulty/${difficultyLevel}`);
  },

  getRecipesByRating: (userId, rating) => {
    return api.get(`/recipe-book/user/${userId}/rating/${rating}`);
  },

  searchRecipes: (userId, query) => {
    return api.get(`/recipe-book/user/${userId}/search`, { params: { query } });
  },

  deleteRecipe: (id) => {
    return api.delete(`/recipe-book/${id}`);
  }
};

export default recipeBookService;