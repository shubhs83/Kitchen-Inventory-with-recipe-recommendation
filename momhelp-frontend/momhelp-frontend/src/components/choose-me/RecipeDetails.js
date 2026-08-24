// import React, { useState, useEffect } from 'react';
// import { Card, Button, Badge, Row, Col, Spinner, Alert, ListGroup } from 'react-bootstrap';
// import recipeService from '../../services/recipeService';
// import LoadingSpinner from '../common/LoadingSpinner';
// import ErrorMessage from '../common/ErrorMessage';

// const RecipeDetails = ({ dish, onBack, onReset, language = 'en' }) => {
//   const [recipe, setRecipe] = useState(null);
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(null);

//   useEffect(() => {
//     if (dish) {
//       fetchRecipeForDish();
//     }
//   }, [dish, language]); // ✅ Re-fetch when language changes

//   const fetchRecipeForDish = async () => {
//     try {
//       setLoading(true);
//       setError(null);
//       // ✅ Pass language to API
//       const response = await recipeService.getChooseMeRecipe(dish.id, language);

//       if (response.data.success) {
//         const apiRecipe = response.data.recipe;
//         const transformedRecipe = {
//           dishName: apiRecipe.dishName,
//           vegetableName: dish.vegetableName,
//           instructions: apiRecipe.instructions,
//           ingredients: apiRecipe.ingredients.map((ing, idx) => ({
//             id: idx,
//             ingredientName: ing.ingredientName,
//             quantity: ing.quantity,
//             unit: ing.unit
//           }))
//         };
//         setRecipe(transformedRecipe);
//       } else {
//         setError('Recipe not found for this dish');
//       }
//     } catch (err) {
//       setError('Failed to load recipe. Please try again.');
//       console.error('Error fetching recipe:', err);
//     } finally {
//       setLoading(false);
//     }
//   };

//   if (loading) {
//     return <LoadingSpinner message="Loading recipe..." />;
//   }

//   if (error) {
//     return (
//       <div>
//         <ErrorMessage message={error} />
//         <div className="text-center mt-3">
//           <Button variant="secondary" onClick={onBack} className="me-2">
//             ← Back to Dishes
//           </Button>
//           <Button variant="success" onClick={onReset}>
//             🏠 Start Over
//           </Button>
//         </div>
//       </div>
//     );
//   }

//   if (!recipe) {
//     return (
//       <Alert variant="warning" className="text-center py-4">
//         <h5>Recipe not available</h5>
//         <p className="mb-3">Sorry, recipe details are not available for this dish.</p>
//         <div>
//           <Button variant="secondary" onClick={onBack} className="me-2">
//             ← Back to Dishes
//           </Button>
//           <Button variant="success" onClick={onReset}>
//             🏠 Start Over
//           </Button>
//         </div>
//       </Alert>
//     );
//   }

//   return (
//     <div>
//       <Card className="mb-4 border-primary shadow">
//         <Card.Header className="bg-primary text-white">
//           <h4 className="mb-0">{recipe.dishName} Recipe</h4>
//           <small className="opacity-75">Made from: {recipe.vegetableName}</small>
//         </Card.Header>
//         <Card.Body>
//           {/* Ingredients Section */}
//           <Row className="mb-4">
//             <Col md={5}>
//               <Card className="h-100">
//                 <Card.Header className="bg-success text-white">
//                   <h5 className="mb-0">📝 Ingredients</h5>
//                 </Card.Header>
//                 <Card.Body>
//                   <ListGroup variant="flush">
//                     {recipe.ingredients && recipe.ingredients.length > 0 ? (
//                       recipe.ingredients.map((ing, index) => (
//                         <ListGroup.Item key={ing.id || index} className="d-flex justify-content-between">
//                           <span>{ing.ingredientName}</span>
//                           <span className="text-success fw-bold">
//                             {ing.quantity} {ing.unit}
//                           </span>
//                         </ListGroup.Item>
//                       ))
//                     ) : (
//                       <ListGroup.Item className="text-muted">
//                         No ingredients listed
//                       </ListGroup.Item>
//                     )}
//                   </ListGroup>
//                 </Card.Body>
//               </Card>
//             </Col>

//             {/* Instructions Section */}
//             <Col md={7}>
//               <Card className="h-100">
//                 <Card.Header className="bg-warning text-dark">
//                   <h5 className="mb-0">👩‍🍳 Cooking Instructions</h5>
//                 </Card.Header>
//                 <Card.Body>
//                   <div className="recipe-instructions">
//                     {recipe.instructions ? (
//                       recipe.instructions.split('\n').map((step, index) => (
//                         step.trim() && (
//                           <div key={index} className="mb-3 d-flex">
//                             <Badge 
//                               bg="success" 
//                               className="me-3 flex-shrink-0" 
//                               style={{width: '30px', height: '30px', paddingTop: '6px'}}
//                             >
//                               {index + 1}
//                             </Badge>
//                             <div className="flex-grow-1">
//                               {step.trim()}
//                             </div>
//                           </div>
//                         )
//                       ))
//                     ) : (
//                       <Alert variant="info">
//                         No cooking instructions available.
//                       </Alert>
//                     )}
//                   </div>
//                 </Card.Body>
//               </Card>
//             </Col>
//           </Row>

//           {/* Tips Section */}
//           <Card className="mb-4">
//             <Card.Header className="bg-info text-white">
//               <h5 className="mb-0">💡 Cooking Tips</h5>
//             </Card.Header>
//             <Card.Body>
//               <Row>
//                 <Col md={4}>
//                   <div className="text-center p-3 border rounded">
//                     <div className="fs-2">🍳</div>
//                     <h6>Preparation</h6>
//                     <small className="text-muted">
//                       Wash all vegetables thoroughly before use
//                     </small>
//                   </div>
//                 </Col>
//                 <Col md={4}>
//                   <div className="text-center p-3 border rounded">
//                     <div className="fs-2">⏰</div>
//                     <h6>Timing</h6>
//                     <small className="text-muted">
//                       Follow the cooking times for best results
//                     </small>
//                   </div>
//                 </Col>
//                 <Col md={4}>
//                   <div className="text-center p-3 border rounded">
//                     <div className="fs-2">👩‍🍳</div>
//                     <h6>Serving</h6>
//                     <small className="text-muted">
//                       Serve hot for best taste and aroma
//                     </small>
//                   </div>
//                 </Col>
//               </Row>
//             </Card.Body>
//           </Card>

//           {/* Navigation Buttons */}
//           <div className="d-flex justify-content-between">
//             <Button variant="secondary" onClick={onBack}>
//               ← Back to Dishes
//             </Button>
//             <div>
//               <Button variant="outline-success" className="me-2" onClick={onReset}>
//                 🏠 Start Over
//               </Button>
//               <Button variant="success">
//                 🖨️ Print Recipe
//               </Button>
//             </div>
//           </div>
//         </Card.Body>
//       </Card>

//       {/* Note Section */}
//       <Alert variant="light" className="text-center">
//         <small className="text-muted">
//           <strong>Note:</strong> Adjust spices and ingredients according to your taste preferences. 
//           This recipe serves approximately {dish?.servings || 4} people.
//         </small>
//       </Alert>
//     </div>
//   );
// };

// export default RecipeDetails;


import React, { useState, useEffect } from 'react';
import { Card, Button, Badge, Row, Col, Alert, ListGroup } from 'react-bootstrap';
import recipeService from '../../services/recipeService';
import LoadingSpinner from '../common/LoadingSpinner';
import ErrorMessage from '../common/ErrorMessage';

const RecipeDetails = ({ dish, onBack, onReset, language = 'en' }) => {
  const [recipe, setRecipe] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (dish) {
      fetchRecipeForDish();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dish, language]); // ✅ Re-fetch when language changes

  const fetchRecipeForDish = async () => {
    try {
      setLoading(true);
      setError(null);
      // ✅ Pass language to API
      const response = await recipeService.getChooseMeRecipe(dish.id, language);

      if (response.data.success) {
        const apiRecipe = response.data.recipe;
        const transformedRecipe = {
          dishName: apiRecipe.dishName,
          vegetableName: dish.vegetableName,
          instructions: apiRecipe.instructions,
          ingredients: apiRecipe.ingredients.map((ing, idx) => ({
            id: idx,
            ingredientName: ing.ingredientName,
            quantity: ing.quantity,
            unit: ing.unit
          }))
        };
        setRecipe(transformedRecipe);
      } else {
        setError(language === 'mr' 
          ? 'या डिशसाठी पाककृती सापडली नाही'
          : 'Recipe not found for this dish');
      }
    } catch (err) {
      setError(language === 'mr' 
        ? 'पाककृती लोड करताना त्रुटी. कृपया पुन्हा प्रयत्न करा.'
        : 'Failed to load recipe. Please try again.');
      console.error('Error fetching recipe:', err);
    } finally {
      setLoading(false);
    }
  };

  const getText = (key) => {
    const translations = {
      loadingMessage: { en: 'Loading recipe...', mr: 'पाककृती लोड होत आहे...' },
      backButton: { en: '← Back to Dishes', mr: '← डिशकडे परत' },
      startOverButton: { en: '🏠 Start Over', mr: '🏠 पुन्हा सुरू करा' },
      printButton: { en: '🖨️ Print Recipe', mr: '🖨️ पाककृती प्रिंट करा' },
      recipeTitle: { en: 'Recipe', mr: 'पाककृती' },
      madeFrom: { en: 'Made from:', mr: 'यापासून बनवलेले:' },
      ingredients: { en: '📝 Ingredients', mr: '📝 साहित्य' },
      cookingInstructions: { en: '👩‍🍳 Cooking Instructions', mr: '👩‍🍳 शिजवण्याची पद्धत' },
      noIngredients: { en: 'No ingredients listed', mr: 'साहित्य सूचीबद्ध नाही' },
      noInstructions: { en: 'No cooking instructions available.', mr: 'शिजवण्याच्या सूचना उपलब्ध नाहीत.' },
      cookingTips: { en: '💡 Cooking Tips', mr: '💡 स्वयंपाकाचे टिप्स' },
      preparationTitle: { en: 'Preparation', mr: 'तयारी' },
      preparationText: { en: 'Wash all vegetables thoroughly before use', mr: 'वापरण्यापूर्वी सर्व भाज्या नीट धुवा' },
      timingTitle: { en: 'Timing', mr: 'वेळ' },
      timingText: { en: 'Follow the cooking times for best results', mr: 'चांगल्या परिणामासाठी शिजवण्याचा वेळ फॉलो करा' },
      servingTitle: { en: 'Serving', mr: 'सर्व्हिंग' },
      servingText: { en: 'Serve hot for best taste and aroma', mr: 'चांगल्या चवसाठी गरम सर्व्ह करा' },
      noteLabel: { en: 'Note:', mr: 'टीप:' },
      noteText: { 
        en: 'Adjust spices and ingredients according to your taste preferences.',
        mr: 'मसाले आणि साहित्य तुमच्या आवडीनुसार समायोजित करा.'
      },
      servesText: { 
        en: 'This recipe serves approximately',
        mr: 'ही पाककृती अंदाजे'
      },
      people: { en: 'people', mr: 'लोकांसाठी' },
      recipeNotAvailable: { en: 'Recipe not available', mr: 'पाककृती उपलब्ध नाही' },
      recipeNotAvailableText: { 
        en: 'Sorry, recipe details are not available for this dish.',
        mr: 'माफ करा, या डिशसाठी पाककृती तपशील उपलब्ध नाहीत.'
      }
    };
    return translations[key]?.[language] || translations[key]?.['en'];
  };

  if (loading) {
    return <LoadingSpinner message={getText('loadingMessage')} />;
  }

  if (error) {
    return (
      <div style={language === 'mr' ? { fontFamily: 'Noto Sans Devanagari, sans-serif' } : {}}>
        <ErrorMessage message={error} />
        <div className="text-center mt-3">
          <Button variant="secondary" onClick={onBack} className="me-2">
            {getText('backButton')}
          </Button>
          <Button variant="success" onClick={onReset}>
            {getText('startOverButton')}
          </Button>
        </div>
      </div>
    );
  }

  if (!recipe) {
    return (
      <Alert variant="warning" className="text-center py-4" 
             style={language === 'mr' ? { fontFamily: 'Noto Sans Devanagari, sans-serif' } : {}}>
        <h5>{getText('recipeNotAvailable')}</h5>
        <p className="mb-3">{getText('recipeNotAvailableText')}</p>
        <div>
          <Button variant="secondary" onClick={onBack} className="me-2">
            {getText('backButton')}
          </Button>
          <Button variant="success" onClick={onReset}>
            {getText('startOverButton')}
          </Button>
        </div>
      </Alert>
    );
  }

  return (
    <div style={language === 'mr' ? { fontFamily: 'Noto Sans Devanagari, sans-serif' } : {}}>
      <Card className="mb-4 border-primary shadow">
        <Card.Header className="bg-primary text-white">
          <h4 className="mb-0">{recipe.dishName} {getText('recipeTitle')}</h4>
          <small className="opacity-75">{getText('madeFrom')} {recipe.vegetableName}</small>
        </Card.Header>
        <Card.Body>
          {/* Ingredients Section */}
          <Row className="mb-4">
            <Col md={5}>
              <Card className="h-100">
                <Card.Header className="bg-success text-white">
                  <h5 className="mb-0">{getText('ingredients')}</h5>
                </Card.Header>
                <Card.Body>
                  <ListGroup variant="flush">
                    {recipe.ingredients && recipe.ingredients.length > 0 ? (
                      recipe.ingredients.map((ing, index) => (
                        <ListGroup.Item key={ing.id || index} className="d-flex justify-content-between">
                          <span>{ing.ingredientName}</span>
                          <span className="text-success fw-bold">
                            {ing.quantity} {ing.unit}
                          </span>
                        </ListGroup.Item>
                      ))
                    ) : (
                      <ListGroup.Item className="text-muted">
                        {getText('noIngredients')}
                      </ListGroup.Item>
                    )}
                  </ListGroup>
                </Card.Body>
              </Card>
            </Col>

            {/* Instructions Section */}
            <Col md={7}>
              <Card className="h-100">
                <Card.Header className="bg-warning text-dark">
                  <h5 className="mb-0">{getText('cookingInstructions')}</h5>
                </Card.Header>
                <Card.Body>
                  <div className="recipe-instructions">
                    {recipe.instructions ? (
                      recipe.instructions.split('\n').map((step, index) => (
                        step.trim() && (
                          <div key={index} className="mb-3 d-flex">
                            <Badge 
                              bg="success" 
                              className="me-3 flex-shrink-0" 
                              style={{width: '30px', height: '30px', paddingTop: '6px'}}
                            >
                              {index + 1}
                            </Badge>
                            <div className="flex-grow-1">
                              {step.trim()}
                            </div>
                          </div>
                        )
                      ))
                    ) : (
                      <Alert variant="info">
                        {getText('noInstructions')}
                      </Alert>
                    )}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          </Row>

          {/* Tips Section */}
          <Card className="mb-4">
            <Card.Header className="bg-info text-white">
              <h5 className="mb-0">{getText('cookingTips')}</h5>
            </Card.Header>
            <Card.Body>
              <Row>
                <Col md={4}>
                  <div className="text-center p-3 border rounded">
                    <div className="fs-2">🍳</div>
                    <h6>{getText('preparationTitle')}</h6>
                    <small className="text-muted">
                      {getText('preparationText')}
                    </small>
                  </div>
                </Col>
                <Col md={4}>
                  <div className="text-center p-3 border rounded">
                    <div className="fs-2">⏰</div>
                    <h6>{getText('timingTitle')}</h6>
                    <small className="text-muted">
                      {getText('timingText')}
                    </small>
                  </div>
                </Col>
                <Col md={4}>
                  <div className="text-center p-3 border rounded">
                    <div className="fs-2">👩‍🍳</div>
                    <h6>{getText('servingTitle')}</h6>
                    <small className="text-muted">
                      {getText('servingText')}
                    </small>
                  </div>
                </Col>
              </Row>
            </Card.Body>
          </Card>

          {/* Navigation Buttons */}
          <div className="d-flex justify-content-between">
            <Button variant="secondary" onClick={onBack}>
              {getText('backButton')}
            </Button>
            <div>
              <Button variant="outline-success" className="me-2" onClick={onReset}>
                {getText('startOverButton')}
              </Button>
              <Button variant="success">
                {getText('printButton')}
              </Button>
            </div>
          </div>
        </Card.Body>
      </Card>

      {/* Note Section */}
      <Alert variant="light" className="text-center">
        <small className="text-muted">
          <strong>{getText('noteLabel')}</strong> {getText('noteText')} {getText('servesText')} {dish?.servings || 4} {getText('people')}.
        </small>
      </Alert>
    </div>
  );
};

export default RecipeDetails;
