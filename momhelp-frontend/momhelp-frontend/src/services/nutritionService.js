import api from './api';

const nutritionService = {
  getNutritionInfo: (foodItem, quantity, unit) => {
    return api.post('/nutrition/get-info', {
      foodItem,
      quantity,
      unit
    });
  },

  getNutritionByVegetable: (vegetableName) => {
    return api.get(`/nutrition/vegetable/${vegetableName}`);
  },

  testService: () => {
    return api.get('/nutrition/test');
  }
};

export default nutritionService;