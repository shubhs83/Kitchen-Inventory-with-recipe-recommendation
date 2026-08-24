import api from './api';

const imageDetectionService = {
  // Detect vegetables from image
  detectVegetables: (imageFile) => {
    const formData = new FormData();
    formData.append('image', imageFile);

    return api.post('/image/detect-vegetables', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },

  // Get supported formats
  getSupportedFormats: () => {
    return api.get('/image/supported-formats');
  },

  // Test endpoint
  testService: () => {
    return api.get('/image/test');
  }
};

export default imageDetectionService;