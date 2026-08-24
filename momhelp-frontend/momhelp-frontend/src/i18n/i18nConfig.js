import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

// Import translation files
import translationEN from './translations/en.json';
import translationHI from './translations/hi.json';
import translationTE from './translations/te.json';
import translationMA from './translations/ma.json'; // ✅ Marathi

// Translation resources
const resources = {
  en: {
    translation: translationEN
  },
  hi: {
    translation: translationHI
  },
  te: {
    translation: translationTE
  },
  ma: { // ✅ Marathi language key
    translation: translationMA
  }
};

i18n
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    resources,
    fallbackLng: 'en',
    lng: localStorage.getItem('selectedLanguage') || 'en',

    interpolation: {
      escapeValue: false
    },

    detection: {
      order: ['localStorage', 'navigator'],
      caches: ['localStorage']
    }
  });

export default i18n;
