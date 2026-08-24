import api from './api';

const API_URL = '/auth';

const authService = {
  signup: (userData) => {
    return api.post(`${API_URL}/signup`, userData);
  },

  login: (credentials) => {
    return api.post(`${API_URL}/login`, credentials);
  },

  getProfile: () => {
    return api.get(`${API_URL}/profile`);
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  // ✅ THIS is what your error is missing
  getCurrentUser: () => {
    const userStr = localStorage.getItem('user');
    if (userStr) {
      try {
        return JSON.parse(userStr);
      } catch (e) {
        return null;
      }
    }
    return null;
  },

  isLoggedIn: () => {
    return !!localStorage.getItem('token');
  },

  getToken: () => {
    return localStorage.getItem('token');
  }
};

export default authService;
