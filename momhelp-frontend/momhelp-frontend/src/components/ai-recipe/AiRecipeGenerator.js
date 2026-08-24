import React, { useState, useEffect } from 'react';
import { Container, Card, Form, Button, Row, Col, Alert, Spinner, Badge } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Select from 'react-select';
import vegetableService from '../../services/vegetableService';
import aiRecipeService from '../../services/aiRecipeService';
import AiRecipeDisplay from './AiRecipeDisplay';

/* ======== ADDED (VOICE SUPPORT IMPORTS) ======== */
import VoiceButton from '../voice/VoiceButton';
import { processVoiceCommand } from '../../utils/voiceCommands';
/* ============================================== */

const AiRecipeGenerator = () => {
  const navigate = useNavigate();

  // Available vegetables from inventory
  const [availableVegetables, setAvailableVegetables] = useState([]);
  const [loadingVegetables, setLoadingVegetables] = useState(true);

  // Form state
  const [selectedVegetables, setSelectedVegetables] = useState([]);
  const [mealType, setMealType] = useState('BREAKFAST');
  const [servings, setServings] = useState(4);
  const [language, setLanguage] = useState('EN');

  // Generation state
  const [generating, setGenerating] = useState(false);
  const [generatedRecipe, setGeneratedRecipe] = useState(null);
  const [error, setError] = useState(null);

  // Fetch available vegetables on component mount
  useEffect(() => {
    fetchAvailableVegetables();
  }, []);

  const fetchAvailableVegetables = async () => {
    try {
      setLoadingVegetables(true);
      const response = await vegetableService.getAvailableVegetables();
      setAvailableVegetables(response.data);
    } catch (err) {
      console.error('Error fetching vegetables:', err);
      setError('Failed to load vegetables');
    } finally {
      setLoadingVegetables(false);
    }
  };

  // Convert vegetables to Select options
  const vegetableOptions = availableVegetables.map(veg => ({
    value: veg.name,
    label: `${veg.name} (${veg.weight} ${veg.unit} available)`
  }));

  /* ======== ADDED (VOICE HANDLER FUNCTION) ======== */
  const handleVoiceInput = (transcript) => {
    const result = processVoiceCommand(transcript);

    if (result.action === 'recipe' && result.vegetables.length > 0) {
      const matchedVegetables = result.vegetables
        .map(vegName => {
          const found = vegetableOptions.find(opt =>
            opt.label.toLowerCase().includes(vegName.toLowerCase())
          );
          return found;
        })
        .filter(Boolean);

      if (matchedVegetables.length > 0) {
        setSelectedVegetables(matchedVegetables);
        toast.success(`Selected: ${matchedVegetables.map(v => v.value).join(', ')}`);
      }
    }
  };
  /* =============================================== */

  const handleGenerateRecipe = async (e) => {
    e.preventDefault();
    setError(null);

    // Validation
    if (selectedVegetables.length === 0) {
      setError('Please select at least one vegetable');
      return;
    }

    if (selectedVegetables.length > 10) {
      setError('Please select maximum 10 vegetables');
      return;
    }

    try {
      setGenerating(true);

      const requestData = {
        vegetables: selectedVegetables.map(v => v.value),
        mealType: mealType,
        servings: parseInt(servings),
        language: language
      };

      const response = await aiRecipeService.generateRecipe(requestData);

      if (response.data.success) {
        setGeneratedRecipe(response.data.data);
        toast.success('Recipe generated successfully! 🎉');
      } else {
        setError(response.data.message || 'Failed to generate recipe');
      }
    } catch (err) {
      console.error('Error generating recipe:', err);
      setError(err.response?.data?.message || 'Failed to generate recipe. Please try again.');
      toast.error('Failed to generate recipe');
    } finally {
      setGenerating(false);
    }
  };

  const handleReset = () => {
    setSelectedVegetables([]);
    setMealType('BREAKFAST');
    setServings(4);
    setLanguage('EN');
    setGeneratedRecipe(null);
    setError(null);
  };

  const handleBack = () => {
    navigate('/');
  };

  return (
    <Container className="py-4">
      <ToastContainer position="top-right" autoClose={3000} />

      <Card className="shadow-lg">
        <Card.Header className="bg-success text-white">
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h4 className="mb-0">🤖 AI Recipe Generator</h4>
              <small>Generate unlimited recipes using AI based on your vegetables</small>
            </div>
            <Badge bg="light" text="dark" className="px-3 py-2">
              ✨ Powered by AI
            </Badge>
          </div>
        </Card.Header>

        <Card.Body>
          <Alert variant="info" className="mb-4">
            <div className="d-flex align-items-center">
              <span className="fs-3 me-3">💡</span>
              <div>
                <strong>How it works:</strong> Select vegetables from your inventory,
                choose meal type and servings, then let AI create a unique recipe for you!
                <br />
                <small className="text-muted">
                  Supports English, Hindi, and Telugu languages
                </small>
              </div>
            </div>
          </Alert>

          {error && (
            <Alert variant="danger" dismissible onClose={() => setError(null)}>
              {error}
            </Alert>
          )}

          {!generatedRecipe && (
            <Form onSubmit={handleGenerateRecipe}>
              <Row>
                {/* Vegetable Selection */}
                <Col md={12} className="mb-4">
                  <Form.Group>
                    <Form.Label className="fw-bold">
                      Select Vegetables <span className="text-danger">*</span>
                      <Badge bg="secondary" className="ms-2">
                        {selectedVegetables.length} selected
                      </Badge>
                    </Form.Label>

                    {/* ======== UPDATED UI (VOICE BUTTON ADDED) ======== */}
                    <div className="d-flex gap-2 align-items-start">
                      <div className="flex-grow-1">
                        <Select
                          isMulti
                          options={vegetableOptions}
                          value={selectedVegetables}
                          onChange={setSelectedVegetables}
                          placeholder="Search and select vegetables from your inventory..."
                          isSearchable
                          isLoading={loadingVegetables}
                          className="react-select-container"
                          classNamePrefix="react-select"
                        />
                      </div>
                      <VoiceButton
                        onTranscript={handleVoiceInput}
                        variant="outline-success"
                        size="md"
                      />
                    </div>

                    <Form.Text className="text-muted">
                      Select 1-10 vegetables from your available stock or use voice: "Recipe with potato and onion"
                    </Form.Text>
                    {/* ================================================== */}
                  </Form.Group>
                </Col>

                {/* Meal Type */}
                <Col md={4} className="mb-4">
                  <Form.Group>
                    <Form.Label className="fw-bold">
                      Meal Type <span className="text-danger">*</span>
                    </Form.Label>
                    <Form.Select
                      value={mealType}
                      onChange={(e) => setMealType(e.target.value)}
                    >
                      <option value="BREAKFAST">🌅 Breakfast</option>
                      <option value="LUNCH_DINNER">🍽️ Lunch/Dinner</option>
                      <option value="DESSERT">🍰 Dessert</option>
                    </Form.Select>
                  </Form.Group>
                </Col>

                {/* Servings */}
                <Col md={4} className="mb-4">
                  <Form.Group>
                    <Form.Label className="fw-bold">
                      Servings <span className="text-danger">*</span>
                    </Form.Label>
                    <Form.Control
                      type="number"
                      min="1"
                      max="20"
                      value={servings}
                      onChange={(e) => setServings(e.target.value)}
                    />
                    <Form.Text className="text-muted">
                      Number of people (1-20)
                    </Form.Text>
                  </Form.Group>
                </Col>

                {/* Language */}
                <Col md={4} className="mb-4">
                  <Form.Group>
                    <Form.Label className="fw-bold">
                      Language <span className="text-danger">*</span>
                    </Form.Label>
                    <Form.Select
                      value={language}
                      onChange={(e) => setLanguage(e.target.value)}
                    >
                      <option value="EN">🇬🇧 English</option>
                      <option value="HI">🇮🇳 Hindi (हिंदी)</option>
                      <option value="TE">🇮🇳 Telugu (తెలుగు)</option>
                    </Form.Select>
                  </Form.Group>
                </Col>
              </Row>

              <div className="d-flex justify-content-between mt-4 pt-3 border-top">
                <Button variant="secondary" onClick={handleBack}>
                  ← Back to Dashboard
                </Button>
                <div>
                  <Button
                    variant="outline-danger"
                    onClick={handleReset}
                    className="me-2"
                    disabled={generating}
                  >
                    🔄 Reset
                  </Button>
                  <Button
                    variant="success"
                    type="submit"
                    disabled={generating || selectedVegetables.length === 0}
                    className="px-4"
                  >
                    {generating ? (
                      <>
                        <Spinner
                          as="span"
                          animation="border"
                          size="sm"
                          role="status"
                          className="me-2"
                        />
                        Generating Recipe...
                      </>
                    ) : (
                      <>🤖 Generate Recipe with AI</>
                    )}
                  </Button>
                </div>
              </div>
            </Form>
          )}

          {generatedRecipe && (
            <AiRecipeDisplay
              recipe={generatedRecipe}
              onGenerateNew={handleReset}
              onBack={handleBack}
            />
          )}

          {generating && (
            <div className="text-center py-5">
              <Spinner animation="border" variant="success" style={{ width: '3rem', height: '3rem' }} />
              <h5 className="mt-3 text-success">AI is creating your recipe...</h5>
              <p className="text-muted">This may take 10-20 seconds</p>
            </div>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default AiRecipeGenerator;
