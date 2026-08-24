// import React, { useState } from 'react';
// import { Container, Breadcrumb, Card, Alert } from 'react-bootstrap';
// import { Link } from 'react-router-dom';
// import AvailableVegetables from './AvailableVegetables';
// import VegetableDishes from './VegetableDishes';
// import RecipeDetails from './RecipeDetails';

// const ChooseMeMain = () => {
//   const [step, setStep] = useState(1); // 1: Select veg, 2: Select dish, 3: View recipe
//   const [selectedVegetable, setSelectedVegetable] = useState(null);
//   const [selectedDish, setSelectedDish] = useState(null);

//   const handleVegetableSelect = (vegetable) => {
//     setSelectedVegetable(vegetable);
//     setStep(2);
//   };

//   const handleDishSelect = (dish) => {
//     setSelectedDish(dish);
//     setStep(3);
//   };

//   const handleBack = () => {
//     if (step === 3) {
//       setSelectedDish(null);
//       setStep(2);
//     } else if (step === 2) {
//       setSelectedVegetable(null);
//       setStep(1);
//     }
//   };

//   const handleReset = () => {
//     setSelectedVegetable(null);
//     setSelectedDish(null);
//     setStep(1);
//   };

//   const renderStep = () => {
//     switch (step) {
//       case 1:
//         return <AvailableVegetables onSelect={handleVegetableSelect} />;
//       case 2:
//         return (
//           <VegetableDishes
//             vegetable={selectedVegetable}
//             onSelect={handleDishSelect}
//             onBack={handleBack}
//           />
//         );
//       case 3:
//         return (
//           <RecipeDetails
//             dish={selectedDish}
//             onBack={handleBack}
//             onReset={handleReset}
//           />
//         );
//       default:
//         return <AvailableVegetables onSelect={handleVegetableSelect} />;
//     }
//   };

//   return (
//     <Container className="py-4">
//       <Card className="shadow">
//         <Card.Header className="bg-success text-white">
//           <h4 className="mb-0">🥬 Choose Me - Recipe Suggestions</h4>
//         </Card.Header>
//         <Card.Body>
//           {/* Breadcrumb Navigation */}
//           <Breadcrumb className="mb-4">
//             <Breadcrumb.Item 
//               active={step === 1} 
//               onClick={() => step > 1 && setStep(1)}
//               className={step > 1 ? 'text-primary cursor-pointer' : ''}
//             >
//               Step 1: Select Vegetable
//             </Breadcrumb.Item>
//             <Breadcrumb.Item 
//               active={step === 2}
//               onClick={() => step > 2 && setStep(2)}
//               className={step > 2 ? 'text-primary cursor-pointer' : ''}
//               disabled={!selectedVegetable}
//             >
//               Step 2: Choose Dish
//             </Breadcrumb.Item>
//             <Breadcrumb.Item active={step === 3} disabled={!selectedDish}>
//               Step 3: View Recipe
//             </Breadcrumb.Item>
//           </Breadcrumb>

//           {/* Current Step Info */}
//           {step === 2 && selectedVegetable && (
//             <Alert variant="info" className="mb-4">
//               <strong>Selected Vegetable:</strong> {selectedVegetable.name} 
//               ({selectedVegetable.weight} {selectedVegetable.unit})
//             </Alert>
//           )}

//           {step === 3 && selectedDish && (
//             <Alert variant="warning" className="mb-4">
//               <strong>Selected Dish:</strong> {selectedDish.dishName}
//               <span className="ms-3">
//                 <button 
//                   className="btn btn-sm btn-outline-success ms-2"
//                   onClick={handleReset}
//                 >
//                   Start Over
//                 </button>
//               </span>
//             </Alert>
//           )}

//           {/* Render Current Step Component */}
//           {renderStep()}
//         </Card.Body>
//       </Card>
//     </Container>
//   );
// };

// export default ChooseMeMain;


import React, { useState } from 'react';
import { Container, Breadcrumb, Card, Alert, ButtonGroup, Button } from 'react-bootstrap';
import AvailableVegetables from './AvailableVegetables';
import VegetableDishes from './VegetableDishes';
import RecipeDetails from './RecipeDetails';

const ChooseMeMain = () => {
  const [step, setStep] = useState(1);
  const [selectedVegetable, setSelectedVegetable] = useState(null);
  const [selectedDish, setSelectedDish] = useState(null);
  const [language, setLanguage] = useState('en'); // 'en' or 'mr' (Marathi)

  const handleVegetableSelect = (vegetable) => {
    setSelectedVegetable(vegetable);
    setStep(2);
  };

  const handleDishSelect = (dish) => {
    setSelectedDish(dish);
    setStep(3);
  };

  const handleBack = () => {
    if (step === 3) {
      setSelectedDish(null);
      setStep(2);
    } else if (step === 2) {
      setSelectedVegetable(null);
      setStep(1);
    }
  };

  const handleReset = () => {
    setSelectedVegetable(null);
    setSelectedDish(null);
    setStep(1);
  };

  const getLanguageText = (key) => {
    const translations = {
      title: {
        en: '🥬 Choose Me - Recipe Suggestions',
        mr: '🥬 मला निवडा - पाककृती सूचना'
      },
      step1: {
        en: 'Step 1: Select Vegetable',
        mr: 'पायरी १: भाजी निवडा'
      },
      step2: {
        en: 'Step 2: Choose Dish',
        mr: 'पायरी २: डिश निवडा'
      },
      step3: {
        en: 'Step 3: View Recipe',
        mr: 'पायरी ३: पाककृती पहा'
      },
      selectedVeg: {
        en: 'Selected Vegetable:',
        mr: 'निवडलेली भाजी:'
      },
      selectedDish: {
        en: 'Selected Dish:',
        mr: 'निवडलेली डिश:'
      },
      startOver: {
        en: 'Start Over',
        mr: 'पुन्हा सुरू करा'
      }
    };
    return translations[key]?.[language] || translations[key]?.['en'];
  };

  const renderStep = () => {
    switch (step) {
      case 1:
        return <AvailableVegetables onSelect={handleVegetableSelect} />;
      case 2:
        return (
          <VegetableDishes
            vegetable={selectedVegetable}
            onSelect={handleDishSelect}
            onBack={handleBack}
            language={language}
          />
        );
      case 3:
        return (
          <RecipeDetails
            dish={selectedDish}
            onBack={handleBack}
            onReset={handleReset}
            language={language}
          />
        );
      default:
        return <AvailableVegetables onSelect={handleVegetableSelect} />;
    }
  };

  return (
    <Container className="py-4" style={language === 'mr' ? { fontFamily: 'Noto Sans Devanagari, sans-serif' } : {}}>
      <Card className="shadow">
        <Card.Header className="bg-success text-white d-flex justify-content-between align-items-center">
          <h4 className="mb-0">{getLanguageText('title')}</h4>
          
          {/* Language Toggle Button */}
          <ButtonGroup>
            <Button
              variant={language === 'en' ? 'light' : 'outline-light'}
              size="sm"
              onClick={() => setLanguage('en')}
            >
              English
            </Button>
            <Button
              variant={language === 'mr' ? 'light' : 'outline-light'}
              size="sm"
              onClick={() => setLanguage('mr')}
            >
              मराठी
            </Button>
          </ButtonGroup>
        </Card.Header>
        
        <Card.Body>
          {/* Breadcrumb Navigation */}
          <Breadcrumb className="mb-4">
            <Breadcrumb.Item 
              active={step === 1} 
              onClick={() => step > 1 && setStep(1)}
              className={step > 1 ? 'text-primary cursor-pointer' : ''}
            >
              {getLanguageText('step1')}
            </Breadcrumb.Item>
            <Breadcrumb.Item 
              active={step === 2}
              onClick={() => step > 2 && setStep(2)}
              className={step > 2 ? 'text-primary cursor-pointer' : ''}
              disabled={!selectedVegetable}
            >
              {getLanguageText('step2')}
            </Breadcrumb.Item>
            <Breadcrumb.Item active={step === 3} disabled={!selectedDish}>
              {getLanguageText('step3')}
            </Breadcrumb.Item>
          </Breadcrumb>

          {/* Current Step Info */}
          {step === 2 && selectedVegetable && (
            <Alert variant="info" className="mb-4">
              <strong>{getLanguageText('selectedVeg')}</strong> {selectedVegetable.name} 
              ({selectedVegetable.weight} {selectedVegetable.unit})
            </Alert>
          )}

          {step === 3 && selectedDish && (
            <Alert variant="warning" className="mb-4">
              <strong>{getLanguageText('selectedDish')}</strong> {selectedDish.dishName}
              <span className="ms-3">
                <button 
                  className="btn btn-sm btn-outline-success ms-2"
                  onClick={handleReset}
                >
                  {getLanguageText('startOver')}
                </button>
              </span>
            </Alert>
          )}

          {/* Render Current Step Component */}
          {renderStep()}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default ChooseMeMain;
