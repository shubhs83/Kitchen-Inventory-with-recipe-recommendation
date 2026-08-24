import api from './api';

const shoppingListService = {
  addItem: (item) => {
    return api.post('/shopping/add', item);
  },

  updateItem: (id, item) => {
    return api.put(`/shopping/${id}`, item);
  },

  markAsPurchased: (id) => {
    return api.patch(`/shopping/${id}/purchase`);
  },

  markAsUnpurchased: (id) => {
    return api.patch(`/shopping/${id}/unpurchase`);
  },

  getAllItems: (userId) => {
    return api.get(`/shopping/user/${userId}`);
  },

  getPendingItems: (userId) => {
    return api.get(`/shopping/user/${userId}/pending`);
  },

  getPurchasedItems: (userId) => {
    return api.get(`/shopping/user/${userId}/purchased`);
  },

  getItemsByCategory: (userId, category) => {
    return api.get(`/shopping/user/${userId}/category/${category}`);
  },

  getItemsByPriority: (userId, priority) => {
    return api.get(`/shopping/user/${userId}/priority/${priority}`);
  },

  deleteItem: (id) => {
    return api.delete(`/shopping/${id}`);
  },

  clearPurchasedItems: (userId) => {
    return api.delete(`/shopping/user/${userId}/purchased`);
  },

  clearAllItems: (userId) => {
    return api.delete(`/shopping/user/${userId}/all`);
  }
};

export default shoppingListService;