import React, { useState } from 'react';
import { Card, Form, Button, Alert } from 'react-bootstrap';
import { FaEnvelope, FaBell, FaCog } from 'react-icons/fa';

const EmailSettingsForm = ({ onSendEmails, isSending }) => {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState(null);
  const [notificationSettings, setNotificationSettings] = useState({
    expiringSoon: true,
    expiringToday: true,
    expired: true,
    dailyDigest: false,
    weeklySummary: false
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!email || !email.includes('@')) {
      setMessage({ type: 'danger', text: 'Please enter a valid email address' });
      return;
    }

    try {
      await onSendEmails(email);
      setMessage({ type: 'success', text: 'Email notifications sent successfully!' });
      setTimeout(() => setMessage(null), 3000);
    } catch (error) {
      setMessage({ type: 'danger', text: 'Failed to send email notifications' });
    }
  };

  const handleSettingChange = (setting) => {
    setNotificationSettings(prev => ({
      ...prev,
      [setting]: !prev[setting]
    }));
  };

  return (
    <Card className="shadow-sm mb-4">
      <Card.Header className="bg-primary text-white">
        <div className="d-flex align-items-center">
          <FaEnvelope className="me-2" />
          <h5 className="mb-0">Email Notifications</h5>
        </div>
      </Card.Header>
      <Card.Body>
        {message && (
          <Alert variant={message.type} dismissible onClose={() => setMessage(null)}>
            {message.text}
          </Alert>
        )}

        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Email Address *</Form.Label>
            <Form.Control
              type="email"
              placeholder="your-email@example.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <Form.Text className="text-muted">
              We'll send expiry alerts to this email
            </Form.Text>
          </Form.Group>

          <div className="mb-4">
            <h6 className="mb-3">
              <FaCog className="me-2" />
              Notification Settings
            </h6>
            
            <div className="d-flex flex-wrap gap-2 mb-3">
              <Button
                variant={notificationSettings.expiringSoon ? "primary" : "outline-primary"}
                size="sm"
                onClick={() => handleSettingChange('expiringSoon')}
              >
                ⏰ Expiring Soon
              </Button>
              <Button
                variant={notificationSettings.expiringToday ? "warning" : "outline-warning"}
                size="sm"
                onClick={() => handleSettingChange('expiringToday')}
              >
                ⚠️ Expiring Today
              </Button>
              <Button
                variant={notificationSettings.expired ? "danger" : "outline-danger"}
                size="sm"
                onClick={() => handleSettingChange('expired')}
              >
                🚨 Expired
              </Button>
            </div>

            <div className="d-flex gap-3">
              <Form.Check
                type="switch"
                id="daily-digest"
                label="Daily Digest"
                checked={notificationSettings.dailyDigest}
                onChange={() => handleSettingChange('dailyDigest')}
              />
              <Form.Check
                type="switch"
                id="weekly-summary"
                label="Weekly Summary"
                checked={notificationSettings.weeklySummary}
                onChange={() => handleSettingChange('weeklySummary')}
              />
            </div>
          </div>

          <Button 
            variant="primary" 
            type="submit" 
            disabled={isSending || !email}
            className="w-100"
          >
            {isSending ? (
              <>
                <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                Sending...
              </>
            ) : (
              <>
                <FaBell className="me-2" />
                Send Email Notifications
              </>
            )}
          </Button>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default EmailSettingsForm;
