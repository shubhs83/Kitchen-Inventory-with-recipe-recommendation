import React, { useState } from 'react';
import { Container, Card, Button, Alert, Row, Col, Badge } from 'react-bootstrap';
import { FaPlay, FaClock, FaBell, FaEnvelope, FaCheck } from 'react-icons/fa';
import api from '../../services/api';

const AutomatedAlertsTest = () => {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [testData, setTestData] = useState({
    created: false,
    vegetables: []
  });

  const triggerTest = async () => {
    setLoading(true);
    setMessage(null);
    
    try {
      const response = await api.post('/automated-alerts/trigger-now');
      const data = response.data;
      
      if (data.success) {
        setMessage({
          type: 'success',
          text: 'Automated alerts triggered! Check console and email.',
          details: data
        });
      } else {
        setMessage({
          type: 'danger',
          text: data.message || 'Failed to trigger alerts'
        });
      }
    } catch (error) {
      setMessage({
        type: 'danger',
        text: 'Error: ' + (error.response?.data?.message || error.message)
      });
    } finally {
      setLoading(false);
    }
  };

  const checkStatus = async () => {
    try {
      const response = await api.get('/automated-alerts/status');
      const data = response.data;
      
      setMessage({
        type: 'info',
        text: 'Service Status',
        details: data
      });
    } catch (error) {
      setMessage({
        type: 'danger',
        text: 'Failed to check status'
      });
    }
  };

  const createTestData = async () => {
    setLoading(true);
    
    try {
      const response = await api.post('/automated-alerts/create-test-data');
      const data = response.data;
      
      if (data.success) {
        setTestData({
          created: true,
          vegetables: [
            { name: 'Test Spinach', status: 'Expires in 3 days', color: 'info' },
            { name: 'Test Tomato', status: 'Expires today', color: 'warning' },
            { name: 'Old Lettuce', status: 'Expired', color: 'danger' }
          ]
        });
        
        setMessage({
          type: 'success',
          text: 'Test vegetables created! Now trigger alerts to test.'
        });
      }
    } catch (error) {
      setMessage({
        type: 'danger',
        text: 'Failed to create test data'
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="mt-4">
      <Card className="shadow-sm mb-4">
        <Card.Header className="bg-primary text-white">
          <h5 className="mb-0"><FaClock /> Automated Expiry Alerts Test</h5>
        </Card.Header>
        <Card.Body>
          <p className="text-muted">
            This system automatically checks for expiring vegetables daily and sends email alerts.
            Alerts are sent for vegetables expiring in 3 days, today, or already expired.
          </p>
          
          {message && (
            <Alert variant={message.type} className="mb-4">
              <h6>{message.text}</h6>
              {message.details && (
                <pre className="mt-2 mb-0" style={{ fontSize: '12px' }}>
                  {JSON.stringify(message.details, null, 2)}
                </pre>
              )}
            </Alert>
          )}

          <Row className="mb-4">
            <Col md={4}>
              <Card className="text-center h-100">
                <Card.Body>
                  <div className="display-4 text-primary mb-3">
                    <FaBell />
                  </div>
                  <Card.Title>Schedule</Card.Title>
                  <Card.Text>
                    Runs daily at <strong>9:00 AM</strong> and <strong>6:00 PM</strong>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
            
            <Col md={4}>
              <Card className="text-center h-100">
                <Card.Body>
                  <div className="display-4 text-success mb-3">
                    <FaEnvelope />
                  </div>
                  <Card.Title>Email Alerts</Card.Title>
                  <Card.Text>
                    Sends 3 types of alerts based on expiry status
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
            
            <Col md={4}>
              <Card className="text-center h-100">
                <Card.Body>
                  <div className="display-4 text-warning mb-3">
                    <FaCheck />
                  </div>
                  <Card.Title>Automatic</Card.Title>
                  <Card.Text>
                    No manual intervention needed
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          </Row>

          <div className="d-grid gap-3">
            <Button 
              variant="primary" 
              size="lg"
              onClick={triggerTest}
              disabled={loading}
            >
              <FaPlay className="me-2" />
              {loading ? 'Triggering...' : 'Trigger Alerts Now'}
            </Button>
            
            <Button 
              variant="outline-info"
              onClick={checkStatus}
            >
              Check Service Status
            </Button>
            
            <Button 
              variant="outline-success"
              onClick={createTestData}
              disabled={testData.created}
            >
              {testData.created ? 'Test Data Created' : 'Create Test Vegetables'}
            </Button>
          </div>
        </Card.Body>
      </Card>

      {testData.created && (
        <Card className="shadow-sm">
          <Card.Header className="bg-info text-white">
            <h6 className="mb-0">📦 Test Vegetables Created</h6>
          </Card.Header>
          <Card.Body>
            <p>Click "Trigger Alerts Now" to test email notifications for:</p>
            <Row>
              {testData.vegetables.map((veg, index) => (
                <Col md={4} key={index} className="mb-3">
                  <Card className={`border-${veg.color}`}>
                    <Card.Body>
                      <h6>{veg.name}</h6>
                      <Badge bg={veg.color}>{veg.status}</Badge>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
            <Alert variant="warning" className="mt-3">
              <small>
                <strong>Note:</strong> For emails to work, update email configuration in 
                <code> application.properties</code> with your Gmail credentials.
              </small>
            </Alert>
          </Card.Body>
        </Card>
      )}
    </Container>
  );
};

export default AutomatedAlertsTest;
