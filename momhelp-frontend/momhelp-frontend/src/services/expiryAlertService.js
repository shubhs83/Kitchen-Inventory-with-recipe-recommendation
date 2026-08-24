import api from './api';

const expiryAlertService = {
  // Create new alert
  createAlert: (alertData) => {
    return api.post('/expiry-alerts/create', alertData);
  },

  // Get all alerts for user
  getAllAlerts: (userId) => {
    return api.get(`/expiry-alerts/user/${userId}`);
  },

  // Get only unread alerts
  getUnreadAlerts: (userId) => {
    return api.get(`/expiry-alerts/user/${userId}/unnotified`);
  },

  // Get alerts by type (EXPIRED, EXPIRING_SOON, EXPIRING_TODAY)
  getAlertsByType: (userId, alertType) => {
    return api.get(`/expiry-alerts/user/${userId}/type/${alertType}`);
  },

  // Mark alert as read/notified
  markAsNotified: (alertId) => {
    return api.patch(`/expiry-alerts/${alertId}/notify`);
  },

  // Generate alerts for expiring vegetables
  generateAlerts: (userId, userEmail) => {
    return api.post(`/expiry-alerts/generate/${userId}`, null, {
      params: { userEmail }
    });
  },

  // Send email notifications
  sendEmailNotifications: (userId, userEmail) => {
    return api.post(`/expiry-alerts/send-emails/${userId}`, null, {
      params: { userEmail }
    });
  },

  // Delete single alert
  deleteAlert: (alertId) => {
    return api.delete(`/expiry-alerts/${alertId}`);
  },

  // Delete all alerts for a vegetable
  deleteAlertsByVegetable: (vegetableId) => {
    return api.delete(`/expiry-alerts/vegetable/${vegetableId}`);
  },

  // Test endpoint
  testConnection: () => {
    return api.get('/expiry-alerts/test-health');
  },

  // Test vegetable connection
  testVegetables: () => {
    return api.get('/expiry-alerts/test-vegetables');
  }
};

export default expiryAlertService;