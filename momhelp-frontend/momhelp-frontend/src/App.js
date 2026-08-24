import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import NutritionCalculator from './components/nutrition/NutritionCalculator';

import Navbar from './components/layout/Navbar';
import Dashboard from './components/dashboard/Dashboard';
import VegetableList from './components/vegetable/VegetableList';
import ChooseMeMain from './components/choose-me/ChooseMeMain';
import AiRecipeGenerator from './components/ai-recipe/AiRecipeGenerator';
import AiRecipeList from './components/ai-recipe/AiRecipeList';
import AutoSuggest from './components/auto-suggest/AutoSuggest';
import LetsUse from './components/lets-use/LetsUse';
import SpoiledVegetables from './components/spoiled/SpoiledVegetables';
import MonthlyReport from './components/reports/MonthlyReport';
import SeasonalRecommendations from './components/seasonal/SeasonalRecommendations';
import VoiceAssistant from './components/voice/VoiceAssistant';
import VegetableImageDetector from './components/image-upload/VegetableImageDetector';
import PreferencesManager from './components/preferences/PreferencesManager';
import ShoppingListManager from './components/shopping/ShoppingListManager';
import MealPlanManager from './components/mealplan/MealPlanManager';
import RecipeBookManager from './components/recipebook/RecipeBookManager';
import CookingTipsManager from './components/cookingtips/CookingTipsManager';
import ExpiryAlertsManager from './components/expiryalerts/ExpiryAlertsManager';
import AutomatedAlertsTest from './components/expiryalerts/AutomatedAlertsTest';

// Auth Components
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import ProtectedRoute from './components/auth/ProtectedRoute';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <main className="py-3">
          <Routes>

            {/* Public Routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />

            {/* Protected Routes */}
            <Route path="/" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
            <Route path="/vegetables" element={<ProtectedRoute><VegetableList /></ProtectedRoute>} />
            <Route path="/view-vegetables" element={<ProtectedRoute><VegetableList /></ProtectedRoute>} />
            <Route path="/choose-me" element={<ProtectedRoute><ChooseMeMain /></ProtectedRoute>} />
            <Route path="/ai-recipe-generator" element={<ProtectedRoute><AiRecipeGenerator /></ProtectedRoute>} />
            <Route path="/ai-recipes" element={<ProtectedRoute><AiRecipeList /></ProtectedRoute>} />
            <Route path="/auto-suggest" element={<ProtectedRoute><AutoSuggest /></ProtectedRoute>} />
            <Route path="/lets-use" element={<ProtectedRoute><LetsUse /></ProtectedRoute>} />
            <Route path="/spoiled" element={<ProtectedRoute><SpoiledVegetables /></ProtectedRoute>} />
            <Route path="/monthly-report" element={<ProtectedRoute><MonthlyReport /></ProtectedRoute>} />
            <Route path="/seasonal" element={<ProtectedRoute><SeasonalRecommendations /></ProtectedRoute>} />
            <Route path="/voice-assistant" element={<ProtectedRoute><VoiceAssistant /></ProtectedRoute>} />
            <Route path="/image-detector" element={<ProtectedRoute><VegetableImageDetector /></ProtectedRoute>} />
            <Route path="/nutrition-calculator" element={<ProtectedRoute><NutritionCalculator /></ProtectedRoute>} />
            <Route path="/preferences" element={<ProtectedRoute><PreferencesManager /></ProtectedRoute>} />
            <Route path="/shopping" element={<ProtectedRoute><ShoppingListManager /></ProtectedRoute>} />
            <Route path="/meal-planner" element={<ProtectedRoute><MealPlanManager /></ProtectedRoute>} />
            <Route path="/recipe-book" element={<ProtectedRoute><RecipeBookManager /></ProtectedRoute>} />
            <Route path="/cooking-tips" element={<ProtectedRoute><CookingTipsManager /></ProtectedRoute>} />
            <Route path="/expiry-alerts" element={<ProtectedRoute><ExpiryAlertsManager /></ProtectedRoute>} />
            <Route path="/automated-alerts-test" element={<ProtectedRoute><AutomatedAlertsTest /></ProtectedRoute>} />

            {/* Fallback */}
            <Route path="*" element={<Navigate to="/login" />} />

          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
