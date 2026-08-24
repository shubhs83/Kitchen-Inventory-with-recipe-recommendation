import api from './api';

const mealPlanService = {
  addMealPlan: (mealPlan) => {
    return api.post('/meal-plans/add', mealPlan);
  },

  updateMealPlan: (id, mealPlan) => {
    return api.put(`/meal-plans/${id}`, mealPlan);
  },

  markAsPrepared: (id) => {
    return api.patch(`/meal-plans/${id}/prepare`);
  },

  markAsUnprepared: (id) => {
    return api.patch(`/meal-plans/${id}/unprepare`);
  },

  getAllMealPlans: (userId) => {
    return api.get(`/meal-plans/user/${userId}`);
  },

  getMealPlansByDate: (userId, date) => {
    return api.get(`/meal-plans/user/${userId}/date/${date}`);
  },

  getMealPlansByDateRange: (userId, startDate, endDate) => {
    return api.get(`/meal-plans/user/${userId}/range`, {
      params: { startDate, endDate }
    });
  },

  getMealPlansByType: (userId, mealType) => {
    return api.get(`/meal-plans/user/${userId}/type/${mealType}`);
  },

  getPendingMealPlans: (userId) => {
    return api.get(`/meal-plans/user/${userId}/pending`);
  },

  deleteMealPlan: (id) => {
    return api.delete(`/meal-plans/${id}`);
  }
};

export default mealPlanService;