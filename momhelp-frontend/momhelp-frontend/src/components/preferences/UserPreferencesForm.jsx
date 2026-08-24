import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card, Badge } from 'react-bootstrap';
import Select from 'react-select';

const UserPreferencesForm = ({ initialData = null, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    userId: 1,
    dietaryPreference: 'VEGETARIAN',
    spiceLevel: 'MEDIUM',
    allergies: [],
    favoriteCuisines: [],
    cookingSkillLevel: 'INTERMEDIATE',
    preferredMealTypes: [],
    avoidIngredients: [],
    maxCookingTime: 60,
    servingSizePreference: 4,
    languagePreference: 'EN'
  });

  const dietaryOptions = [
    { value: 'VEGETARIAN', label: '🥗 Vegetarian' },
    { value: 'NON_VEGETARIAN', label: '🍗 Non-Vegetarian' },
    { value: 'VEGAN', label: '🌱 Vegan' }
  ];

  const spiceLevelOptions = [
    { value: 'MILD', label: '😊 Mild' },
    { value: 'MEDIUM', label: '🌶️ Medium' },
    { value: 'HOT', label: '🔥 Hot' }
  ];

  const skillLevelOptions = [
    { value: 'BEGINNER', label: '👶 Beginner' },
    { value: 'INTERMEDIATE', label: '👨‍🍳 Intermediate' },
    { value: 'ADVANCED', label: '⭐ Advanced' }
  ];

  const allergyOptions = [
    { value: 'nuts', label: 'Nuts' },
    { value: 'dairy', label: 'Dairy' },
    { value: 'gluten', label: 'Gluten' },
    { value: 'soy', label: 'Soy' },
    { value: 'eggs', label: 'Eggs' },
    { value: 'shellfish', label: 'Shellfish' },
    { value: 'fish', label: 'Fish' },
    { value: 'peanuts', label: 'Peanuts' }
  ];

  const cuisineOptions = [
    { value: 'Indian', label: '🇮🇳 Indian' },
    { value: 'Chinese', label: '🇨🇳 Chinese' },
    { value: 'Italian', label: '🇮🇹 Italian' },
    { value: 'Mexican', label: '🇲🇽 Mexican' },
    { value: 'Thai', label: '🇹🇭 Thai' },
    { value: 'Japanese', label: '🇯🇵 Japanese' },
    { value: 'American', label: '🇺🇸 American' },
    { value: 'Mediterranean', label: '🌊 Mediterranean' }
  ];

  const mealTypeOptions = [
    { value: 'BREAKFAST', label: '🌅 Breakfast' },
    { value: 'LUNCH', label: '☀️ Lunch' },
    { value: 'DINNER', label: '🌙 Dinner' },
    { value: 'SNACKS', label: '🍪 Snacks' }
  ];

  const languageOptions = [
    { value: 'EN', label: 'English' },
    { value: 'HI', label: 'हिंदी (Hindi)' },
    { value: 'TE', label: 'తెలుగు (Telugu)' }
  ];

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        allergies: initialData.allergies || [],
        favoriteCuisines: initialData.favoriteCuisines || [],
        preferredMealTypes: initialData.preferredMealTypes || [],
        avoidIngredients: initialData.avoidIngredients || []
      });
    }
  }, [initialData]);

  const handleChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleMultiSelectChange = (field, selectedOptions) => {
    const values = selectedOptions ? selectedOptions.map(opt => opt.value) : [];
    setFormData(prev => ({ ...prev, [field]: values }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const getSelectValue = (options, value) => {
    return options.find(opt => opt.value === value) || null;
  };

  const getMultiSelectValue = (options, values) => {
    return options.filter(opt => values.includes(opt.value));
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-primary text-white">
        <h5 className="mb-0">⚙️ {initialData ? 'Update' : 'Set'} Your Preferences</h5>
      </Card.Header>
      <Card.Body>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>🍽️ Dietary Preference</Form.Label>
                <Select
                  options={dietaryOptions}
                  value={getSelectValue(dietaryOptions, formData.dietaryPreference)}
                  onChange={(opt) => handleChange('dietaryPreference', opt.value)}
                  placeholder="Select dietary preference"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>🌶️ Spice Level</Form.Label>
                <Select
                  options={spiceLevelOptions}
                  value={getSelectValue(spiceLevelOptions, formData.spiceLevel)}
                  onChange={(opt) => handleChange('spiceLevel', opt.value)}
                  placeholder="Select spice level"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>👨‍🍳 Cooking Skill Level</Form.Label>
                <Select
                  options={skillLevelOptions}
                  value={getSelectValue(skillLevelOptions, formData.cookingSkillLevel)}
                  onChange={(opt) => handleChange('cookingSkillLevel', opt.value)}
                  placeholder="Select skill level"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>🌐 Language Preference</Form.Label>
                <Select
                  options={languageOptions}
                  value={getSelectValue(languageOptions, formData.languagePreference)}
                  onChange={(opt) => handleChange('languagePreference', opt.value)}
                  placeholder="Select language"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>⏱️ Max Cooking Time (minutes)</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.maxCookingTime}
                  onChange={(e) => handleChange('maxCookingTime', parseInt(e.target.value))}
                  min="15"
                  max="180"
                />
              </Form.Group>
            </Col>

            <Col md={6} className="mb-3">
              <Form.Group>
                <Form.Label>👥 Default Serving Size</Form.Label>
                <Form.Control
                  type="number"
                  value={formData.servingSizePreference}
                  onChange={(e) => handleChange('servingSizePreference', parseInt(e.target.value))}
                  min="1"
                  max="12"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>🚫 Allergies</Form.Label>
                <Select
                  isMulti
                  options={allergyOptions}
                  value={getMultiSelectValue(allergyOptions, formData.allergies)}
                  onChange={(opts) => handleMultiSelectChange('allergies', opts)}
                  placeholder="Select allergies (if any)"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>❤️ Favorite Cuisines</Form.Label>
                <Select
                  isMulti
                  options={cuisineOptions}
                  value={getMultiSelectValue(cuisineOptions, formData.favoriteCuisines)}
                  onChange={(opts) => handleMultiSelectChange('favoriteCuisines', opts)}
                  placeholder="Select your favorite cuisines"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>🍴 Preferred Meal Types</Form.Label>
                <Select
                  isMulti
                  options={mealTypeOptions}
                  value={getMultiSelectValue(mealTypeOptions, formData.preferredMealTypes)}
                  onChange={(opts) => handleMultiSelectChange('preferredMealTypes', opts)}
                  placeholder="Select preferred meal types"
                />
              </Form.Group>
            </Col>

            <Col md={12} className="mb-3">
              <Form.Group>
                <Form.Label>⛔ Ingredients to Avoid</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Enter ingredients separated by commas (e.g., onion, garlic)"
                  value={formData.avoidIngredients.join(', ')}
                  onChange={(e) => {
                    const values = e.target.value.split(',').map(v => v.trim()).filter(v => v);
                    handleChange('avoidIngredients', values);
                  }}
                />
                <Form.Text className="text-muted">
                  Separate multiple ingredients with commas
                </Form.Text>
              </Form.Group>
            </Col>
          </Row>

          <div className="d-flex gap-2 justify-content-end mt-3">
            {onCancel && (
              <Button variant="secondary" onClick={onCancel}>
                Cancel
              </Button>
            )}
            <Button variant="primary" type="submit">
              {initialData ? 'Update Preferences' : 'Save Preferences'}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default UserPreferencesForm;