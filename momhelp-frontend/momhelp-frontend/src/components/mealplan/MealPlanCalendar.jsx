import React, { useState } from 'react';
import { Card, Row, Col, Badge, Button } from 'react-bootstrap';
import MealPlanCard from './MealPlanCard';

const MealPlanCalendar = ({ mealPlans, onEdit, onDelete, onTogglePrepared }) => {
  const [viewMode, setViewMode] = useState('week');
  const [currentDate, setCurrentDate] = useState(new Date());

  const getWeekDates = (date) => {
    const week = [];
    const current = new Date(date);
    current.setDate(current.getDate() - current.getDay());

    for (let i = 0; i < 7; i++) {
      week.push(new Date(current));
      current.setDate(current.getDate() + 1);
    }
    return week;
  };

  const formatDate = (date) => date.toISOString().split('T')[0];

  const getDayName = (date) =>
    date.toLocaleDateString('en-US', { weekday: 'short' });

  const getMealsForDate = (date) => {
    const dateStr = formatDate(date);
    return mealPlans
      .filter(plan => {
        const planDate = new Date(plan.mealDate).toISOString().split('T')[0];
        return planDate === dateStr;
      })
      .sort((a, b) => {
        const order = { BREAKFAST: 1, LUNCH: 2, SNACK: 3, DINNER: 4 };
        return (order[a.mealType] || 5) - (order[b.mealType] || 5);
      });
  };

  const goToPreviousWeek = () => {
    const newDate = new Date(currentDate);
    newDate.setDate(newDate.getDate() - 7);
    setCurrentDate(newDate);
  };

  const goToNextWeek = () => {
    const newDate = new Date(currentDate);
    newDate.setDate(newDate.getDate() + 7);
    setCurrentDate(newDate);
  };

  const goToToday = () => setCurrentDate(new Date());

  const weekDates = getWeekDates(currentDate);
  const isToday = (date) => formatDate(date) === formatDate(new Date());

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-primary text-white">
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="mb-0">📅 Weekly Meal Plan</h5>
          <div className="d-flex gap-2">
            <Button variant="light" size="sm" onClick={goToPreviousWeek}>
              ← Previous
            </Button>
            <Button variant="light" size="sm" onClick={goToToday}>
              Today
            </Button>
            <Button variant="light" size="sm" onClick={goToNextWeek}>
              Next →
            </Button>
          </div>
        </div>
      </Card.Header>
      <Card.Body>
        <Row>
          {weekDates.map((date, index) => {
            const dayMeals = getMealsForDate(date);
            return (
              <Col key={index} md={12} lg={6} xl={4} className="mb-3">
                <Card className={`h-100 ${isToday(date) ? 'border-primary border-2' : ''}`}>
                  <Card.Header className={isToday(date) ? 'bg-primary text-white' : 'bg-light'}>
                    <div className="d-flex justify-content-between align-items-center">
                      <strong>{getDayName(date)}</strong>
                      <span>{date.getDate()}/{date.getMonth() + 1}</span>
                    </div>
                    {isToday(date) && <Badge bg="warning" text="dark">Today</Badge>}
                  </Card.Header>
                  <Card.Body className="p-2">
                    {dayMeals.length === 0 ? (
                      <p className="text-muted text-center small mb-0">No meals planned</p>
                    ) : (
                      dayMeals.map(meal => (
                        <MealPlanCard
                          key={meal.id}
                          mealPlan={meal}
                          onEdit={onEdit}
                          onDelete={onDelete}
                          onTogglePrepared={onTogglePrepared}
                        />
                      ))
                    )}
                  </Card.Body>
                </Card>
              </Col>
            );
          })}
        </Row>
      </Card.Body>
    </Card>
  );
};

export default MealPlanCalendar;
