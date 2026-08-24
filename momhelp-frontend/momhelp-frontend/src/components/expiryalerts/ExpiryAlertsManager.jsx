import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner, Card } from 'react-bootstrap';
import { FaSync } from 'react-icons/fa';
import ExpiryAlertList from './ExpiryAlertList';
import EmailSettingsForm from './EmailSettingsForm';
import expiryAlertService from '../../services/expiryAlertService';

const ExpiryAlertsManager = () => {
  const [alerts, setAlerts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sending, setSending] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [stats, setStats] = useState({
    total: 0,
    expired: 0,
    today: 0,
    soon: 0,
    unread: 0
  });

  const userId = 1; // Replace with actual user ID from auth
  const userEmail = "user@example.com"; // Replace with actual user email

  useEffect(() => {
    loadAlerts();
  }, []);

  useEffect(() => {
    calculateStats();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [alerts]);

  const calculateStats = () => {
    const expired = alerts.filter(a => a.alertType === 'EXPIRED').length;
    const today = alerts.filter(a => a.alertType === 'EXPIRING_TODAY').length;
    const soon = alerts.filter(a => a.alertType === 'EXPIRING_SOON').length;
    const unread = alerts.filter(a => !a.isNotified).length;
    
    setStats({
      total: alerts.length,
      expired,
      today,
      soon,
      unread
    });
  };

  const loadAlerts = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await expiryAlertService.getAllAlerts(userId);
      if (response.data.success) {
        setAlerts(response.data.data);
      }
    } catch (err) {
      console.error('Error loading alerts:', err);
      setAlerts([]);
    } finally {
      setLoading(false);
    }
  };

  const handleGenerateAlerts = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await expiryAlertService.generateAlerts(userId, userEmail);
      if (response.data.success) {
        setSuccess(response.data.message);
        await loadAlerts();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error generating alerts:', err);
      setError('Failed to generate alerts');
    } finally {
      setLoading(false);
    }
  };

  const handleSendEmails = async (email) => {
    try {
      setSending(true);
      setError(null);
      const response = await expiryAlertService.sendEmailNotifications(userId, email);
      if (response.data.success) {
        setSuccess(response.data.message);
        await loadAlerts();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error sending emails:', err);
      setError('Failed to send email notifications');
      throw err;
    } finally {
      setSending(false);
    }
  };

  const handleMarkAsRead = async (alertId) => {
    try {
      setLoading(true);
      setError(null);
      const response = await expiryAlertService.markAsNotified(alertId);
      if (response.data.success) {
        await loadAlerts();
      }
    } catch (err) {
      console.error('Error marking alert as read:', err);
      setError('Failed to update alert');
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteAlert = async (alertId) => {
    if (window.confirm('Are you sure you want to delete this alert?')) {
      try {
        setLoading(true);
        setError(null);
        const response = await expiryAlertService.deleteAlert(alertId);
        if (response.data.success) {
          setSuccess(response.data.message);
          await loadAlerts();
          setTimeout(() => setSuccess(null), 3000);
        }
      } catch (err) {
        console.error('Error deleting alert:', err);
        setError('Failed to delete alert');
      } finally {
        setLoading(false);
      }
    }
  };

  const handleClearAll = async () => {
    if (window.confirm('Are you sure you want to clear all alerts?')) {
      try {
        setLoading(true);
        setError(null);
        const deletePromises = alerts.map(alert => 
          expiryAlertService.deleteAlert(alert.id)
        );
        await Promise.all(deletePromises);
        setSuccess('All alerts cleared!');
        await loadAlerts();
        setTimeout(() => setSuccess(null), 3000);
      } catch (err) {
        console.error('Error clearing alerts:', err);
        setError('Failed to clear alerts');
      } finally {
        setLoading(false);
      }
    }
  };

  if (loading && alerts.length === 0) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="danger" />
        <p className="mt-2">Loading expiry alerts...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2>🔔 Inventory Expiry Alerts</h2>
              <p className="text-muted">
                Monitor expiring vegetables and receive email notifications
              </p>
            </div>
            <div className="d-flex gap-2">
              <Button 
                variant="outline-danger"
                onClick={handleClearAll}
                disabled={alerts.length === 0}
              >
                Clear All
              </Button>
              <Button 
                variant="danger"
                onClick={handleGenerateAlerts}
                disabled={loading}
              >
                <FaSync className="me-2" />
                Generate Alerts
              </Button>
            </div>
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

      {/* Stats Cards */}
      <Row className="mb-4">
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-primary">
            <Card.Body>
              <h3>{stats.total}</h3>
              <p className="mb-0 text-muted">Total Alerts</p>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-danger">
            <Card.Body>
              <h3 className="text-danger">{stats.expired}</h3>
              <p className="mb-0 text-muted">Expired</p>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-warning">
            <Card.Body>
              <h3 className="text-warning">{stats.today}</h3>
              <p className="mb-0 text-muted">Expiring Today</p>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-info">
            <Card.Body>
              <h3 className="text-info">{stats.soon}</h3>
              <p className="mb-0 text-muted">Expiring Soon</p>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-success">
            <Card.Body>
              <h3 className="text-success">{stats.unread}</h3>
              <p className="mb-0 text-muted">Unread</p>
            </Card.Body>
          </Card>
        </Col>
        
        <Col md={2} sm={6} className="mb-3">
          <Card className="text-center border-dark">
            <Card.Body>
              <h3>{alerts.filter(a => a.emailSent).length}</h3>
              <p className="mb-0 text-muted">Emails Sent</p>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row>
        <Col lg={8}>
          <ExpiryAlertList
            alerts={alerts}
            onMarkAsRead={handleMarkAsRead}
            onDelete={handleDeleteAlert}
          />
        </Col>

        <Col lg={4}>
          <EmailSettingsForm 
            onSendEmails={handleSendEmails}
            isSending={sending}
          />
          
          {/* Quick Actions Card */}
          <Card className="shadow-sm mt-4">
            <Card.Header className="bg-info text-white">
              <h6 className="mb-0">⚡ Quick Actions</h6>
            </Card.Header>
            <Card.Body>
              <div className="d-grid gap-2">
                <Button variant="outline-primary" onClick={() => handleGenerateAlerts()}>
                  🔄 Scan for Expiring Vegetables
                </Button>
                <Button variant="outline-success" onClick={() => handleMarkAsRead('all')}>
                  ✅ Mark All as Read
                </Button>
                <Button variant="outline-warning" onClick={() => loadAlerts()}>
                  📊 Refresh Alerts
                </Button>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ExpiryAlertsManager;
