import React, { useState } from 'react';
import { Container, Row, Col, Card, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import './Dashboard.css';

const Dashboard = () => {
  const [activeSection, setActiveSection] = useState(null);

  const menuItems = [
    {
      title: 'Inside the Freeze',
      description: 'Add, Update, Delete Vegetables',
      link: '/vegetables',
      color: 'primary',
      icon: '🥬'
    },
    {
      title: 'Choose Me',
      description: 'Recipe suggestions based on vegetable',
      link: '/choose-me',
      color: 'warning',
      icon: '📋'
    },
    {
      title: '🤖 AI Recipe Generator',
      description: 'Generate unlimited recipes using AI',
      link: '/ai-recipe-generator',
      color: 'success',
      icon: '✨'
    },
    {
      title: 'My AI Recipes',
      description: 'View all AI generated recipes',
      link: '/ai-recipes',
      color: 'info',
      icon: '📚'
    },
    {
      title: 'Auto Suggest',
      description: 'Meal recommendations for different dishes',
      link: '/auto-suggest',
      color: 'success',
      icon: '✨'
    },
    {
      title: "Let's Use",
      description: 'Update vegetable quantities after use',
      link: '/lets-use',
      color: 'secondary',
      icon: '🛒'
    },
    {
      title: 'Spoiled Vegetables',
      description: 'View and remove expired vegetables',
      link: '/spoiled',
      color: 'danger',
      icon: '⚠️'
    },
    {
      title: 'Monthly Reports',
      description: 'View usage history and analytics',
      link: '/monthly-report',
      color: 'primary',
      icon: '📊'
    },
    {
      title: '🥗 Nutrition Calculator',
      description: 'Get nutritional information for vegetables',
      link: '/nutrition-calculator',
      color: 'success',
      icon: '📊'
    },
    {
      title: 'Seasonal Dishes',
      description: 'Season-based recipe recommendations',
      link: '/seasonal',
      color: 'info',
      icon: '🌦️'
    },
    {
      title: '📅 Meal Planner',
      description: 'Plan weekly meals and track preparation',
      link: '/meal-planner',
      color: 'primary',
      icon: '🗓️'
    },
    {
      title: '🛒 Shopping List',
      description: 'Manage grocery shopping with categories and priorities',
      link: '/shopping',
      color: 'success',
      icon: '📝'
    },
    {
      title: '📖 Recipe Book',
      description: 'Save and organize your favorite recipes',
      link: '/recipe-book',
      color: 'warning',
      icon: '📚'
    },
    {
      title: '🔔 Expiry Alerts',
      description: 'Monitor expiring vegetables and get email notifications',
      link: '/expiry-alerts',
      color: 'danger',
      icon: '⚠️'
    }
  ];

  const sectionMap = {
    inventory: ['Inside the Freeze', 'Choose Me', "Let's Use"],
    smart: ['Auto Suggest', '🤖 AI Recipe Generator', 'My AI Recipes', 'Seasonal Dishes'],
    waste: ['🔔 Expiry Alerts', 'Spoiled Vegetables'],
    planning: [
      'Monthly Reports',
      '🥗 Nutrition Calculator',
      '🛒 Shopping List',
      '📅 Meal Planner',
      '📖 Recipe Book'
    ]
  };

  const renderSubCards = (sectionKey) => (
    <>
      <Button
        variant="secondary"
        className="mb-4 back-btn"
        onClick={() => setActiveSection(null)}
      >
        ⬅ Back
      </Button>

      <Row className="g-4">
        {menuItems
          .filter(item => sectionMap[sectionKey].includes(item.title))
          .map((item, index) => (
            <Col key={index} lg={4} md={6}>
              <Card className="h-100 sub-card fade-in">
                <Card.Body className="text-center">
                  <div className="section-icon">{item.icon}</div>
                  <Card.Title>{item.title}</Card.Title>
                  <Card.Text className="text-muted">{item.description}</Card.Text>
                  <Button as={Link} to={item.link} variant={item.color} className="w-100">
                    Open
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
      </Row>
    </>
  );

  return (
    <Container fluid className="py-5 dashboard-bg">
      <Container>
        <div className="dashboard-hero text-center fade-in">
          <h1 className="display-5 fw-bold">Kitchen Inventory with Recipe Recommendation</h1>
          <p className="lead">"Your kitchen, our innovation, your delight."</p>
          <p>Streamline your meal planning and reduce food waste</p>
        </div>

        {/* SUB SECTIONS */}
        {activeSection === 'inventory' && renderSubCards('inventory')}
        {activeSection === 'smart' && renderSubCards('smart')}
        {activeSection === 'waste' && renderSubCards('waste')}
        {activeSection === 'planning' && renderSubCards('planning')}

        {/* MAIN 4 SECTIONS */}
        {!activeSection && (
          <Row className="g-4">
            <Col md={6}>
              <Card
                className="h-100 text-center main-section-card fade-in"
                onClick={() => setActiveSection('inventory')}
              >
                <Card.Body>
                  <div className="section-icon">🥕</div>
                  <Card.Title>Kitchen & Inventory</Card.Title>
                  <Card.Text>Manage vegetables, usage & quick recipes</Card.Text>
                </Card.Body>
              </Card>
            </Col>

            <Col md={6}>
              <Card
                className="h-100 text-center main-section-card fade-in"
                onClick={() => setActiveSection('smart')}
              >
                <Card.Body>
                  <div className="section-icon">🤖</div>
                  <Card.Title>Smart Recipes & Suggestions</Card.Title>
                  <Card.Text>AI recipes, auto suggestions & seasonal dishes</Card.Text>
                </Card.Body>
              </Card>
            </Col>

            <Col md={6}>
              <Card
                className="h-100 text-center main-section-card fade-in"
                onClick={() => setActiveSection('waste')}
              >
                <Card.Body>
                  <div className="section-icon">⚠️</div>
                  <Card.Title>Waste & Expiry</Card.Title>
                  <Card.Text>Track spoiled items & expiry alerts</Card.Text>
                </Card.Body>
              </Card>
            </Col>

            <Col md={6}>
              <Card
                className="h-100 text-center main-section-card fade-in"
                onClick={() => setActiveSection('planning')}
              >
                <Card.Body>
                  <div className="section-icon">📊</div>
                  <Card.Title>Planning & Utilities</Card.Title>
                  <Card.Text>Reports, nutrition, shopping & meal planning</Card.Text>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        )}
      </Container>
    </Container>
  );
};

export default Dashboard;
