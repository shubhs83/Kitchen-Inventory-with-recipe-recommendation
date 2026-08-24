// import React, { useState, useEffect } from 'react';
// import { Row, Col, Card, Button, Badge, Spinner, Alert } from 'react-bootstrap';
// import dishService from '../../services/dishService';
// import LoadingSpinner from '../common/LoadingSpinner';
// import ErrorMessage from '../common/ErrorMessage';

// const VegetableDishes = ({ vegetable, onSelect, onBack }) => {
//   const [dishes, setDishes] = useState([]);
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState(null);

//   useEffect(() => {
//     if (vegetable) {
//       fetchDishesForVegetable();
//     }
//   }, [vegetable]);

//  const fetchDishesForVegetable = async () => {
//   try {
//     setLoading(true);
//     setError(null);
//     const response = await dishService.getChooseMeDishes(vegetable.id);
//     if (response.data.success) {
//       // Transform Spoonacular response to match our format
//       const transformedDishes = response.data.dishes.map(dish => ({
//         id: dish.id,
//         dishName: dish.dishName,
//         description: `Uses ${dish.usedIngredientCount} ingredient(s) from your stock`,
//         vegetableName: vegetable.name,
//         difficultyLevel: dish.missedIngredientCount <= 3 ? 'Easy' : 'Medium',
//         prepTime: 15,
//         cookTime: 30,
//         servings: 4,
//         image: dish.image
//       }));
//       setDishes(transformedDishes);
//     } else {
//       setError('No dishes found for this vegetable');
//     }
//   } catch (err) {
//     setError('Failed to load dishes. Please try again.');
//     console.error('Error fetching dishes:', err);
//   } finally {
//     setLoading(false);
//   }
// };

//   const handleDishSelect = (dish) => {
//     onSelect(dish);
//   };

//   const getDifficultyBadge = (level) => {
//     const colors = {
//       'Easy': 'success',
//       'Medium': 'warning',
//       'Hard': 'danger'
//     };
//     return colors[level] || 'secondary';
//   };

//   const formatTime = (minutes) => {
//     if (minutes < 60) return `${minutes} mins`;
//     const hours = Math.floor(minutes / 60);
//     const mins = minutes % 60;
//     return `${hours}h ${mins > 0 ? `${mins}m` : ''}`.trim();
//   };

//   if (loading) {
//     return <LoadingSpinner message="Loading dishes..." />;
//   }

//   if (error) {
//     return (
//       <div>
//         <Alert variant="danger">
//           {error}
//           <div className="mt-3">
//             <Button variant="secondary" onClick={onBack}>
//               ← Back to Vegetables
//             </Button>
//           </div>
//         </Alert>
//       </div>
//     );
//   }

//   if (dishes.length === 0) {
//     return (
//       <Alert variant="info" className="text-center py-4">
//         <h5>No dishes found for {vegetable.name}</h5>
//         <p className="mb-3">Try another vegetable or check back later.</p>
//         <Button variant="secondary" onClick={onBack}>
//           ← Back to Vegetables
//         </Button>
//       </Alert>
//     );
//   }

//   return (
//     <div>
//       <div className="text-center mb-4">
//         <h5 className="text-warning">What can you make with {vegetable.name}?</h5>
//         <p className="text-muted">
//           Select a dish to view its detailed recipe
//         </p>
//       </div>

//       <Row className="g-4">
//         {dishes.map((dish) => (
//           <Col key={dish.id} lg={6} md={12}>
//             <Card className="h-100 shadow-sm border-warning hover-lift">
//               <Card.Body>
//                 <div className="d-flex justify-content-between align-items-start mb-3">
//                   <div>
//                     <Card.Title className="fw-bold">{dish.dishName}</Card.Title>
//                     <Card.Subtitle className="text-muted mb-2">
//                       {dish.description}
//                     </Card.Subtitle>
//                   </div>
//                   <Badge bg={getDifficultyBadge(dish.difficultyLevel)}>
//                     {dish.difficultyLevel}
//                   </Badge>
//                 </div>
                
//                 <div className="mb-3">
//                   <small className="text-muted">
//                     <span className="me-3">⏱️ Prep: {formatTime(dish.prepTime)}</span>
//                     <span className="me-3">🔥 Cook: {formatTime(dish.cookTime)}</span>
//                     <span>👨‍👩‍👧‍👦 Serves: {dish.servings}</span>
//                   </small>
//                 </div>

//                 <div className="d-flex justify-content-between align-items-center">
//                   <div>
//                     <small className="text-success">
//                       🥬 Made from: {dish.vegetableName}
//                     </small>
//                   </div>
//                   <Button
//                     variant="warning"
//                     onClick={() => handleDishSelect(dish)}
//                     size="sm"
//                   >
//                     View Recipe →
//                   </Button>
//                 </div>
//               </Card.Body>
//             </Card>
//           </Col>
//         ))}
//       </Row>

//       <div className="text-center mt-4">
//         <Button variant="outline-secondary" onClick={onBack}>
//           ← Back to Vegetable Selection
//         </Button>
//       </div>
//     </div>
//   );
// };

// export default VegetableDishes;

import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Button, Badge, Alert } from 'react-bootstrap';
import dishService from '../../services/dishService';
import LoadingSpinner from '../common/LoadingSpinner';

const VegetableDishes = ({ vegetable, onSelect, onBack, language = 'en' }) => {
  const [dishes, setDishes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (vegetable) {
      fetchDishesForVegetable();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [vegetable, language]); // ✅ Re-fetch when language changes

  const fetchDishesForVegetable = async () => {
    try {
      setLoading(true);
      setError(null);
      // ✅ Pass language parameter to API
      const response = await dishService.getChooseMeDishes(vegetable.id, language);
      
      if (response.data.success) {
        const transformedDishes = response.data.dishes.map(dish => ({
          id: dish.id,
          dishName: dish.dishName,
          dishNameEnglish: dish.dishNameEnglish,
          description: language === 'mr' 
            ? `तुमच्या स्टॉकमधून ${dish.usedIngredientCount} घटक वापरतो`
            : `Uses ${dish.usedIngredientCount} ingredient(s) from your stock`,
          vegetableName: vegetable.name,
          difficultyLevel: dish.missedIngredientCount <= 3 ? 
            (language === 'mr' ? 'सोपे' : 'Easy') : 
            (language === 'mr' ? 'मध्यम' : 'Medium'),
          prepTime: 15,
          cookTime: 30,
          servings: 4,
          image: dish.image
        }));
        setDishes(transformedDishes);
      } else {
        setError(language === 'mr' 
          ? 'या भाजीसाठी डिश सापडल्या नाहीत'
          : 'No dishes found for this vegetable');
      }
    } catch (err) {
      setError(language === 'mr' 
        ? 'डिश लोड करताना त्रुटी. कृपया पुन्हा प्रयत्न करा.'
        : 'Failed to load dishes. Please try again.');
      console.error('Error fetching dishes:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDishSelect = (dish) => {
    onSelect(dish);
  };

  const getDifficultyBadge = (level) => {
    const marathiLevels = { 'सोपे': 'success', 'मध्यम': 'warning', 'कठीण': 'danger' };
    const englishLevels = { 'Easy': 'success', 'Medium': 'warning', 'Hard': 'danger' };
    return language === 'mr' ? marathiLevels[level] : englishLevels[level];
  };

  const formatTime = (minutes) => {
    if (language === 'mr') {
      if (minutes < 60) return `${minutes} मिनिटे`;
      const hours = Math.floor(minutes / 60);
      const mins = minutes % 60;
      return `${hours} तास ${mins > 0 ? `${mins} मिनिटे` : ''}`.trim();
    } else {
      if (minutes < 60) return `${minutes} mins`;
      const hours = Math.floor(minutes / 60);
      const mins = minutes % 60;
      return `${hours}h ${mins > 0 ? `${mins}m` : ''}`.trim();
    }
  };

  const getText = (key) => {
    const translations = {
      loadingMessage: { en: 'Loading dishes...', mr: 'डिश लोड होत आहेत...' },
      backButton: { en: '← Back to Vegetables', mr: '← भाज्यांकडे परत' },
      noDishesTitle: { en: 'No dishes found for', mr: 'यासाठी डिश सापडल्या नाहीत' },
      noDishesText: { en: 'Try another vegetable or check back later.', mr: 'दुसरी भाजी वापरून पहा किंवा नंतर तपासा.' },
      heading: { en: 'What can you make with', mr: 'यापासून काय बनवता येईल' },
      subheading: { en: 'Select a dish to view its detailed recipe', mr: 'तपशीलवार पाककृती पाहण्यासाठी डिश निवडा' },
      prepLabel: { en: 'Prep:', mr: 'तयारी:' },
      cookLabel: { en: 'Cook:', mr: 'शिजवणे:' },
      servesLabel: { en: 'Serves:', mr: 'सर्व्हिंग:' },
      madeFromLabel: { en: 'Made from:', mr: 'यापासून बनवलेले:' },
      viewRecipeButton: { en: 'View Recipe →', mr: 'पाककृती पहा →' }
    };
    return translations[key]?.[language] || translations[key]?.['en'];
  };

  if (loading) {
    return <LoadingSpinner message={getText('loadingMessage')} />;
  }

  if (error) {
    return (
      <div>
        <Alert variant="danger">
          {error}
          <div className="mt-3">
            <Button variant="secondary" onClick={onBack}>
              {getText('backButton')}
            </Button>
          </div>
        </Alert>
      </div>
    );
  }

  if (dishes.length === 0) {
    return (
      <Alert variant="info" className="text-center py-4">
        <h5>{getText('noDishesTitle')} {vegetable.name}</h5>
        <p className="mb-3">{getText('noDishesText')}</p>
        <Button variant="secondary" onClick={onBack}>
          {getText('backButton')}
        </Button>
      </Alert>
    );
  }

  return (
    <div style={language === 'mr' ? { fontFamily: 'Noto Sans Devanagari, sans-serif' } : {}}>
      <div className="text-center mb-4">
        <h5 className="text-warning">
          {getText('heading')} {vegetable.name}?
        </h5>
        <p className="text-muted">
          {getText('subheading')}
        </p>
      </div>

      <Row className="g-4">
        {dishes.map((dish) => (
          <Col key={dish.id} lg={6} md={12}>
            <Card className="h-100 shadow-sm border-warning hover-lift">
              <Card.Body>
                <div className="d-flex justify-content-between align-items-start mb-3">
                  <div>
                    <Card.Title className="fw-bold">{dish.dishName}</Card.Title>
                    <Card.Subtitle className="text-muted mb-2">
                      {dish.description}
                    </Card.Subtitle>
                  </div>
                  <Badge bg={getDifficultyBadge(dish.difficultyLevel)}>
                    {dish.difficultyLevel}
                  </Badge>
                </div>
                
                <div className="mb-3">
                  <small className="text-muted">
                    <span className="me-3">⏱️ {getText('prepLabel')} {formatTime(dish.prepTime)}</span>
                    <span className="me-3">🔥 {getText('cookLabel')} {formatTime(dish.cookTime)}</span>
                    <span>👨‍👩‍👧‍👦 {getText('servesLabel')} {dish.servings}</span>
                  </small>
                </div>

                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <small className="text-success">
                      🥬 {getText('madeFromLabel')} {dish.vegetableName}
                    </small>
                  </div>
                  <Button
                    variant="warning"
                    onClick={() => handleDishSelect(dish)}
                    size="sm"
                  >
                    {getText('viewRecipeButton')}
                  </Button>
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      <div className="text-center mt-4">
        <Button variant="outline-secondary" onClick={onBack}>
          {getText('backButton')}
        </Button>
      </div>
    </div>
  );
};

export default VegetableDishes;
