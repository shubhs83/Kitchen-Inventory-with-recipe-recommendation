// Voice command processing and text-to-speech utilities

export const processVoiceCommand = (transcript) => {
  const lowerTranscript = transcript.toLowerCase().trim();
  
  // Navigation commands
  if (lowerTranscript.includes('go to dashboard') || lowerTranscript.includes('dashboard')) {
    return { action: 'navigate', path: '/' };
  }
  
  if (lowerTranscript.includes('add vegetable') || lowerTranscript.includes('add vegetables')) {
    return { action: 'navigate', path: '/vegetables' };
  }
  
  if (lowerTranscript.includes('view vegetable') || lowerTranscript.includes('show vegetables')) {
    return { action: 'navigate', path: '/view-vegetables' };
  }
  
  if (lowerTranscript.includes('spoiled vegetable') || lowerTranscript.includes('expired vegetables')) {
    return { action: 'navigate', path: '/spoiled' };
  }
  
  if (lowerTranscript.includes('ai recipe') || lowerTranscript.includes('generate recipe')) {
    return { action: 'navigate', path: '/ai-recipe-generator' };
  }
  
  if (lowerTranscript.includes('choose me') || lowerTranscript.includes('recipe suggestion')) {
    return { action: 'navigate', path: '/choose-me' };
  }
  
  if (lowerTranscript.includes('auto suggest') || lowerTranscript.includes('surprise me')) {
    return { action: 'navigate', path: '/auto-suggest' };
  }
  
  if (lowerTranscript.includes('monthly report') || lowerTranscript.includes('usage report')) {
    return { action: 'navigate', path: '/monthly-report' };
  }
  
  // Search commands
  if (lowerTranscript.includes('search for') || lowerTranscript.includes('find')) {
    const searchTerm = extractSearchTerm(lowerTranscript);
    return { action: 'search', query: searchTerm };
  }
  
  // Vegetable extraction for AI recipe
  if (lowerTranscript.includes('recipe with') || lowerTranscript.includes('make with')) {
    const vegetables = extractVegetables(lowerTranscript);
    return { action: 'recipe', vegetables };
  }
  
  return { action: 'unknown', transcript };
};

const extractSearchTerm = (transcript) => {
  const patterns = [
    /search for (.+)/i,
    /find (.+)/i,
    /look for (.+)/i
  ];
  
  for (const pattern of patterns) {
    const match = transcript.match(pattern);
    if (match) {
      return match[1].trim();
    }
  }
  
  return '';
};

const extractVegetables = (transcript) => {
  const commonVegetables = [
    'potato', 'tomato', 'onion', 'spinach', 'carrot', 'cabbage',
    'cauliflower', 'broccoli', 'pea', 'corn', 'ladyfinger', 'okra',
    'eggplant', 'brinjal', 'capsicum', 'pepper', 'cucumber', 'pumpkin'
  ];
  
  const foundVegetables = [];
  const lowerTranscript = transcript.toLowerCase();
  
  commonVegetables.forEach(veg => {
    if (lowerTranscript.includes(veg)) {
      foundVegetables.push(veg);
    }
  });
  
  return foundVegetables;
};

// Text-to-Speech utility
export const speak = (text, options = {}) => {
  if ('speechSynthesis' in window) {
    // Cancel any ongoing speech
    window.speechSynthesis.cancel();
    
    const utterance = new SpeechSynthesisUtterance(text);
    utterance.lang = options.lang || 'en-IN';
    utterance.rate = options.rate || 1;
    utterance.pitch = options.pitch || 1;
    utterance.volume = options.volume || 1;
    
    window.speechSynthesis.speak(utterance);
  }
};

// Stop speaking
export const stopSpeaking = () => {
  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
  }
};

// Voice command help text
export const getVoiceCommandsHelp = () => {
  return [
    { command: '"Go to Dashboard"', description: 'Navigate to main dashboard' },
    { command: '"Add Vegetable"', description: 'Go to add vegetables page' },
    { command: '"View Vegetables"', description: 'Show all vegetables' },
    { command: '"Spoiled Vegetables"', description: 'Show expired vegetables' },
    { command: '"AI Recipe" or "Generate Recipe"', description: 'Open AI recipe generator' },
    { command: '"Choose Me"', description: 'Get recipe suggestions' },
    { command: '"Auto Suggest" or "Surprise Me"', description: 'Get random dish suggestion' },
    { command: '"Monthly Report"', description: 'View usage statistics' },
    { command: '"Search for [vegetable name]"', description: 'Search for specific vegetable' },
    { command: '"Recipe with potato and onion"', description: 'Generate recipe with specific vegetables' }
  ];
};