import React from 'react';
import { Card, Badge, Button } from 'react-bootstrap';
import { FaBell, FaTrash, FaEnvelope, FaCalendar } from 'react-icons/fa';

const ExpiryAlertCard = ({ alert, onMarkAsRead, onDelete }) => {
  const getAlertColor = (alertType) => {
    const colors = {
      EXPIRED: 'danger',
      EXPIRING_TODAY: 'warning',
      EXPIRING_SOON: 'info'
    };
    return colors[alertType] || 'secondary';
  };

  const getAlertIcon = (alertType) => {
    const icons = {
      EXPIRED: '🚨',
      EXPIRING_TODAY: '⚠️',
      EXPIRING_SOON: '⏰'
    };
    return icons[alertType] || '📢';
  };

  const getAlertMessage = (alert) => {
    if (alert.alertType === 'EXPIRED') {
      return `${alert.vegetableName} has expired!`;
    } else if (alert.alertType === 'EXPIRING_TODAY') {
      return `${alert.vegetableName} expires today!`;
    } else {
      return `${alert.vegetableName} expires in ${alert.daysUntilExpiry} day(s)`;
    }
  };

  const formatDate = (date) => {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  return (
    <Card 
      className={`mb-3 shadow-sm border-${getAlertColor(alert.alertType)}`}
      style={{ 
        opacity: alert.isNotified ? 0.8 : 1,
        borderWidth: '2px'
      }}
    >
      <Card.Body>
        <div className="d-flex justify-content-between align-items-start">
          <div className="flex-grow-1">
            <div className="d-flex align-items-center gap-2 mb-2">
              <span style={{ fontSize: '1.5rem' }}>
                {getAlertIcon(alert.alertType)}
              </span>
              <h6 className="mb-0">{getAlertMessage(alert)}</h6>
            </div>

            <div className="d-flex flex-wrap gap-2 mb-2">
              <Badge bg={getAlertColor(alert.alertType)}>
                {alert.alertType.replace('_', ' ')}
              </Badge>
              <Badge bg="secondary">
                <FaCalendar className="me-1" />
                {formatDate(alert.expiryDate)}
              </Badge>
              {alert.emailSent && (
                <Badge bg="success">
                  <FaEnvelope className="me-1" /> Email Sent
                </Badge>
              )}
            </div>

            <div className="text-muted small">
              Alert created: {formatDate(alert.createdDate)}
            </div>
          </div>

          <div className="d-flex flex-column gap-2">
            {!alert.isNotified && (
              <Button
                variant="outline-success"
                size="sm"
                onClick={() => onMarkAsRead(alert.id)}
                title="Mark as read"
              >
                <FaBell />
              </Button>
            )}
            <Button
              variant="outline-danger"
              size="sm"
              onClick={() => onDelete(alert.id)}
              title="Delete alert"
            >
              <FaTrash />
            </Button>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

export default ExpiryAlertCard;