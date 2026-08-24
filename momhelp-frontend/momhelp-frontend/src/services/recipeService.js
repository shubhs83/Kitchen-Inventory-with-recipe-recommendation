// import api from './api';

// const recipeService = {
//   // Get recipe by dish ID
//   getRecipeByDishId: (dishId) => {
//     return api.get(`/recipes/by-dish/${dishId}`);
//   },

//   // Get recipe by recipe ID
//   getRecipeById: (recipeId) => {
//     return api.get(`/recipes/${recipeId}`);
//   },

//   // Choose Me flow: Get recipe for dish
//   getChooseMeRecipe: (dishId) => {
//     return api.get(`/choose-me/recipe/${dishId}`);
//   }
// };

// export default recipeService;

import api from './api';

const recipeService = {
  // Get recipe by dish ID
  getRecipeByDishId: (dishId) => {
    return api.get(`/recipes/by-dish/${dishId}`);
  },

  // Get recipe by recipe ID
  getRecipeById: (recipeId) => {
    return api.get(`/recipes/${recipeId}`);
  },

  // ✅ NEW: Choose Me flow with language support
  getChooseMeRecipe: (dishId, language = 'en') => {
    return api.get(`/choose-me/recipe/${dishId}`, {
      params: { language }
    });
  }
};

export default recipeService;