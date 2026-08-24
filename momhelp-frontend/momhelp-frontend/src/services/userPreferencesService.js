import api from './api';

const userPreferencesService = {
  savePreferences: (preferences) => {
    return api.post('/preferences/save', preferences);
  },

  updatePreferences: (userId, preferences) => {
    return api.put(`/preferences/${userId}`, preferences);
  },

  getPreferences: (userId) => {
    return api.get(`/preferences/${userId}`);
  },

  getDefaultPreferences: () => {
    return api.get('/preferences/default');
  },

  deletePreferences: (userId) => {
    return api.delete(`/preferences/${userId}`);
  }
};

export default userPreferencesService;