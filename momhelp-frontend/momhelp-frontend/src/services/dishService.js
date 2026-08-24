// import api from './api';

// const dishService = {
//   // Get all dishes
//   getAllDishes: () => {
//     return api.get('/dishes/all');
//   },

//   // Get dish by ID
//   getDishById: (id) => {
//     return api.get(`/dishes/${id}`);
//   },

//   // Get dishes by vegetable ID
//   getDishesByVegetableId: (vegetableId) => {
//     return api.get(`/dishes/by-vegetable/${vegetableId}`);
//   },

//   // Get available dishes for a vegetable (for Choose Me flow)
//   getAvailableDishesByVegetableId: (vegetableId) => {
//     return api.get(`/dishes/available/${vegetableId}`);
//   },

//   // Get dishes for available vegetables
//   getDishesForAvailableVegetables: () => {
//     return api.get('/dishes/available');
//   },

//   // Get vegetables that have dishes
//   getVegetablesWithDishes: () => {
//     return api.get('/dishes/vegetables-with-dishes');
//   },

//   // Choose Me flow: Get dishes for vegetable
//   getChooseMeDishes: (vegetableId) => {
//     return api.get(`/choose-me/dishes/${vegetableId}`);
//   }

//   // REMOVED: getChooseMeRecipe from here (it belongs in recipeService)
// };

// export default dishService;


import api from './api';

const dishService = {
  // Get all dishes
  getAllDishes: () => {
    return api.get('/dishes/all');
  },

  // Get dish by ID
  getDishById: (id) => {
    return api.get(`/dishes/${id}`);
  },

  // Get dishes by vegetable ID
  getDishesByVegetableId: (vegetableId) => {
    return api.get(`/dishes/by-vegetable/${vegetableId}`);
  },

  // Get available dishes for a vegetable (for Choose Me flow)
  getAvailableDishesByVegetableId: (vegetableId) => {
    return api.get(`/dishes/available/${vegetableId}`);
  },

  // Get dishes for available vegetables
  getDishesForAvailableVegetables: () => {
    return api.get('/dishes/available');
  },

  // Get vegetables that have dishes
  getVegetablesWithDishes: () => {
    return api.get('/dishes/vegetables-with-dishes');
  },

  // ✅ NEW: Choose Me flow with language support
  getChooseMeDishes: (vegetableId, language = 'en') => {
    return api.get(`/choose-me/dishes/${vegetableId}`, {
      params: { language }
    });
  }
};

export default dishService;