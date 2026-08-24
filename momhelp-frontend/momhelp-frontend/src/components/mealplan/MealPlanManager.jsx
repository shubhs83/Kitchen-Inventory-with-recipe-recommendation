import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner, Tabs, Tab } from 'react-bootstrap';
import MealPlanForm from './MealPlanForm';
import MealPlanCalendar from './MealPlanCalendar';
import MealPlanCard from './MealPlanCard';
import mealPlanService from '../../services/mealPlanService';

const MealPlanManager = () => {
  const [mealPlans, setMealPlans] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingPlan, setEditingPlan] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [activeTab, setActiveTab] = useState('calendar');

  const userId = 1;

  useEffect(() => {
    loadMealPlans();
  }, []);

  const loadMealPlans = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await mealPlanService.getAllMealPlans(userId);
      if (response.data.success) {
        setMealPlans(response.data.data);
      }
    } catch (err) {
      console.error('Error loading meal plans:', err);
      setMealPlans([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAddPlan = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await mealPlanService.addMealPlan(formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        await loadMealPlans();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error adding meal plan:', err);
      setError(err.response?.data?.message || 'Failed to add meal plan');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdatePlan = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await mealPlanService.updateMealPlan(editingPlan.id, formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        setEditingPlan(null);
        await loadMealPlans();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error updating meal plan:', err);
      setError(err.response?.data?.message || 'Failed to update meal plan');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (formData) => {
    if (editingPlan) {
      handleUpdatePlan(formData);
    } else {
      handleAddPlan(formData);
    }
  };

  const handleEdit = (plan) => {
    setEditingPlan(plan);
    setShowForm(true);
    setError(null);
    setSuccess(null);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this meal plan?')) {
      try {
        setLoading(true);
        setError(null);
        const response = await mealPlanService.deleteMealPlan(id);
        if (response.data.success) {
          setSuccess(response.data.message);
          await loadMealPlans();
          setTimeout(() => setSuccess(null), 3000);
        }
      } catch (err) {
        console.error('Error deleting meal plan:', err);
        setError(err.response?.data?.message || 'Failed to delete meal plan');
      } finally {
        setLoading(false);
      }
    }
  };

  const handleTogglePrepared = async (id, currentStatus) => {
    try {
      setLoading(true);
      setError(null);
      const response = currentStatus
        ? await mealPlanService.markAsUnprepared(id)
        : await mealPlanService.markAsPrepared(id);

      if (response.data.success) {
        await loadMealPlans();
      }
    } catch (err) {
      console.error('Error toggling prepared status:', err);
      setError('Failed to update status');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingPlan(null);
    setError(null);
    setSuccess(null);
  };

  const pendingPlans = mealPlans.filter(plan => !plan.isPrepared);
  const preparedPlans = mealPlans.filter(plan => plan.isPrepared);

  if (loading && mealPlans.length === 0) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="primary" />
        <p className="mt-2">Loading meal plans...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2>📅 Meal Planner</h2>
              <p className="text-muted">
                Plan your meals for the week and track preparation
              </p>
            </div>
            <Button
              variant="primary"
              onClick={() => {
                setShowForm(!showForm);
                setEditingPlan(null);
              }}
            >
              {showForm ? '📋 View Calendar' : '➕ Add Meal Plan'}
            </Button>
          </div>
        </Col>
      </Row>

      {error && (
        <Alert variant="danger" dismissible onClose={() => setError(null)}>
          {error}
        </Alert>
      )}

      {success && (
        <Alert variant="success" dismissible onClose={() => setSuccess(null)}>
          {success}
        </Alert>
      )}

      <Row>
        <Col>
          {showForm ? (
            <MealPlanForm
              initialData={editingPlan}
              onSubmit={handleSubmit}
              onCancel={handleCancel}
            />
          ) : (
            <Tabs
              activeKey={activeTab}
              onSelect={(k) => setActiveTab(k)}
              className="mb-3"
            >
              <Tab eventKey="calendar" title="📅 Weekly Calendar">
                <MealPlanCalendar
                  mealPlans={mealPlans}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onTogglePrepared={handleTogglePrepared}
                />
              </Tab>

              <Tab eventKey="pending" title={`⏳ Pending (${pendingPlans.length})`}>
                {pendingPlans.length === 0 ? (
                  <p className="text-center text-muted">No pending meals</p>
                ) : (
                  pendingPlans.map(plan => (
                    <MealPlanCard
                      key={plan.id}
                      mealPlan={plan}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                      onTogglePrepared={handleTogglePrepared}
                    />
                  ))
                )}
              </Tab>

              <Tab eventKey="prepared" title={`✅ Prepared (${preparedPlans.length})`}>
                {preparedPlans.length === 0 ? (
                  <p className="text-center text-muted">No prepared meals</p>
                ) : (
                  preparedPlans.map(plan => (
                    <MealPlanCard
                      key={plan.id}
                      mealPlan={plan}
                      onEdit={handleEdit}
                      onDelete={handleDelete}
                      onTogglePrepared={handleTogglePrepared}
                    />
                  ))
                )}
              </Tab>
            </Tabs>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default MealPlanManager;
