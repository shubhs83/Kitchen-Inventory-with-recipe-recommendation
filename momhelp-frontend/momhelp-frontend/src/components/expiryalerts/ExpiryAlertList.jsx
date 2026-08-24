import React from 'react';
import { Card, Tabs, Tab, Badge, Alert } from 'react-bootstrap';
import ExpiryAlertCard from './ExpiryAlertCard';

const ExpiryAlertList = ({ alerts, onMarkAsRead, onDelete }) => {
  const expiredAlerts = alerts.filter(a => a.alertType === 'EXPIRED');
  const todayAlerts = alerts.filter(a => a.alertType === 'EXPIRING_TODAY');
  const soonAlerts = alerts.filter(a => a.alertType === 'EXPIRING_SOON');
  const unreadAlerts = alerts.filter(a => !a.isNotified);

  const getTotalUnread = () => {
    return unreadAlerts.length;
  };

  const getCriticalCount = () => {
    return expiredAlerts.length + todayAlerts.length;
  };

  return (
    <Card className="shadow-sm">
      <Card.Header className="bg-danger text-white">
        <div className="d-flex justify-content-between align-items-center">
          <h5 className="mb-0">🔔 Expiry Alerts</h5>
          <div>
            {getCriticalCount() > 0 && (
              <Badge bg="warning" className="me-2">
                {getCriticalCount()} Critical
              </Badge>
            )}
            <Badge bg="light" text="dark">
              {getTotalUnread()} Unread
            </Badge>
          </div>
        </div>
      </Card.Header>
      <Card.Body>
        {alerts.length === 0 ? (
          <Alert variant="info">
            <div className="text-center">
              <h5>No alerts found</h5>
              <p className="mb-0">Generate alerts to see expiring vegetables here</p>
            </div>
          </Alert>
        ) : (
          <Tabs defaultActiveKey="all" className="mb-3">
            <Tab eventKey="all" title={`All (${alerts.length})`}>
              {alerts.map(alert => (
                <ExpiryAlertCard
                  key={alert.id}
                  alert={alert}
                  onMarkAsRead={onMarkAsRead}
                  onDelete={onDelete}
                />
              ))}
            </Tab>

            <Tab eventKey="critical" title={`🚨 Critical (${getCriticalCount()})`}>
              {[...expiredAlerts, ...todayAlerts].length === 0 ? (
                <Alert variant="success" className="text-center">
                  No critical alerts! 🎉
                </Alert>
              ) : (
                [...expiredAlerts, ...todayAlerts].map(alert => (
                  <ExpiryAlertCard
                    key={alert.id}
                    alert={alert}
                    onMarkAsRead={onMarkAsRead}
                    onDelete={onDelete}
                  />
                ))
              )}
            </Tab>

            <Tab eventKey="expired" title={`⚠️ Expired (${expiredAlerts.length})`}>
              {expiredAlerts.length === 0 ? (
                <Alert variant="success" className="text-center">
                  No expired items! 👍
                </Alert>
              ) : (
                expiredAlerts.map(alert => (
                  <ExpiryAlertCard
                    key={alert.id}
                    alert={alert}
                    onMarkAsRead={onMarkAsRead}
                    onDelete={onDelete}
                  />
                ))
              )}
            </Tab>

            <Tab eventKey="today" title={`📅 Today (${todayAlerts.length})`}>
              {todayAlerts.length === 0 ? (
                <Alert variant="info" className="text-center">
                  Nothing expiring today
                </Alert>
              ) : (
                todayAlerts.map(alert => (
                  <ExpiryAlertCard
                    key={alert.id}
                    alert={alert}
                    onMarkAsRead={onMarkAsRead}
                    onDelete={onDelete}
                  />
                ))
              )}
            </Tab>

            <Tab eventKey="soon" title={`⏰ Soon (${soonAlerts.length})`}>
              {soonAlerts.length === 0 ? (
                <Alert variant="info" className="text-center">
                  Nothing expiring soon
                </Alert>
              ) : (
                soonAlerts.map(alert => (
                  <ExpiryAlertCard
                    key={alert.id}
                    alert={alert}
                    onMarkAsRead={onMarkAsRead}
                    onDelete={onDelete}
                  />
                ))
              )}
            </Tab>

            <Tab eventKey="unread" title={`📬 Unread (${unreadAlerts.length})`}>
              {unreadAlerts.length === 0 ? (
                <Alert variant="success" className="text-center">
                  All alerts read! ✅
                </Alert>
              ) : (
                unreadAlerts.map(alert => (
                  <ExpiryAlertCard
                    key={alert.id}
                    alert={alert}
                    onMarkAsRead={onMarkAsRead}
                    onDelete={onDelete}
                  />
                ))
              )}
            </Tab>
          </Tabs>
        )}
      </Card.Body>
    </Card>
  );
};

export default ExpiryAlertList;