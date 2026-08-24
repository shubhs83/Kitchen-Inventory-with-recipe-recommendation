import api from './api';

const cookingTipService = {
  addTip: (tip) => {
    return api.post('/cooking-tips/add', tip);
  },

  updateTip: (id, tip) => {
    return api.put(`/cooking-tips/${id}`, tip);
  },

  toggleFavorite: (id) => {
    return api.patch(`/cooking-tips/${id}/favorite`);
  },

  incrementViewCount: (id) => {
    return api.patch(`/cooking-tips/${id}/view`);
  },

  incrementHelpfulCount: (id) => {
    return api.patch(`/cooking-tips/${id}/helpful`);
  },

  getAllTips: (userId) => {
    return api.get(`/cooking-tips/user/${userId}`);
  },

  getTipById: (id) => {
    return api.get(`/cooking-tips/${id}`);
  },

  getFavoriteTips: (userId) => {
    return api.get(`/cooking-tips/user/${userId}/favorites`);
  },

  getTipsByCategory: (userId, category) => {
    return api.get(`/cooking-tips/user/${userId}/category/${category}`);
  },

  getTipsByDifficulty: (userId, difficultyLevel) => {
    return api.get(`/cooking-tips/user/${userId}/difficulty/${difficultyLevel}`);
  },

  searchTips: (userId, query) => {
    return api.get(`/cooking-tips/user/${userId}/search`, { params: { query } });
  },

  getMostViewedTips: (userId) => {
    return api.get(`/cooking-tips/user/${userId}/most-viewed`);
  },

  getMostHelpfulTips: (userId) => {
    return api.get(`/cooking-tips/user/${userId}/most-helpful`);
  },

  deleteTip: (id) => {
    return api.delete(`/cooking-tips/${id}`);
  }
};

export default cookingTipService;