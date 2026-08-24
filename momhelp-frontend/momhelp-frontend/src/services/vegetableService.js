import api from './api';

const vegetableService = {
  // Get all vegetables
  getAllVegetables: () => {
    return api.get('/vegetables/all');
  },

  // Get vegetable by ID
  getVegetableById: (id) => {
    return api.get(`/vegetables/${id}`);
  },

  // Add new vegetable
  addVegetable: (vegetableData) => {
    return api.post('/vegetables/add', vegetableData);
  },

  // Update vegetable
  updateVegetable: (id, vegetableData) => {
    return api.put(`/vegetables/update/${id}`, vegetableData);
  },

  // Delete vegetable
  deleteVegetable: (id) => {
    return api.delete(`/vegetables/delete/${id}`);
  },

  // Search vegetables by name
  searchVegetables: (name) => {
    return api.get(`/vegetables/search?name=${encodeURIComponent(name)}`);
  },

  // Get available vegetables (not expired)
  getAvailableVegetables: () => {
    return api.get('/vegetables/available');
  },

  // Get spoiled vegetables
  getSpoiledVegetables: () => {
    return api.get('/vegetables/spoiled');
  },

  // Mark vegetable as spoiled
  markAsSpoiled: (id) => {
    return api.put(`/vegetables/mark-spoiled/${id}`);
  },

  // Remove spoiled vegetable
  removeSpoiledVegetable: (id) => {
    return api.delete(`/vegetables/remove-spoiled/${id}`);
  },
};

export default vegetableService;