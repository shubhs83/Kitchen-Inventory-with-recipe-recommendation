import React from 'react';
import { Table, Button, Badge } from 'react-bootstrap';
import { format } from 'date-fns';

const VegetableTable = ({ vegetables, onEdit, onDelete, showActions = true }) => {
  const formatDate = (dateString) => {
    try {
      return format(new Date(dateString), 'dd-MMM-yyyy');
    } catch {
      return dateString;
    }
  };

  const isExpired = (useBeforeDate) => {
    return new Date(useBeforeDate) < new Date();
  };

  // Calculate days remaining until expiry
  const getDaysRemaining = (useBeforeDate) => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const expiryDate = new Date(useBeforeDate);
    expiryDate.setHours(0, 0, 0, 0);
    const diffTime = expiryDate - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  };

  // Get badge variant based on days remaining
  const getExpiryBadge = (useBeforeDate) => {
    const daysRemaining = getDaysRemaining(useBeforeDate);
    
    if (daysRemaining < 0) {
      return <Badge bg="danger">Expired</Badge>;
    } else if (daysRemaining === 0) {
      return <Badge bg="danger">Expires Today!</Badge>;
    } else if (daysRemaining <= 2) {
      return <Badge bg="warning">⚠️ {daysRemaining} day{daysRemaining !== 1 ? 's' : ''} left</Badge>;
    } else if (daysRemaining <= 5) {
      return <Badge bg="info">📅 {daysRemaining} days left</Badge>;
    } else {
      return <Badge bg="success">Fresh</Badge>;
    }
  };

  return (
    <div className="table-responsive">
      <Table striped bordered hover className="align-middle">
        <thead className="table-dark">
          <tr>
            <th>#</th>
            <th>Vegetable</th>
            <th>Weight</th>
            <th>Unit</th>
            <th>Added On</th>
            <th>Use Before</th>
            <th>Status</th>
            {showActions && <th>Actions</th>}
          </tr>
        </thead>
        <tbody>
          {vegetables.length === 0 ? (
            <tr>
              <td colSpan={showActions ? 8 : 7} className="text-center py-4 text-muted">
                No vegetables found
              </td>
            </tr>
          ) : (
            vegetables.map((veg, index) => {
              const expired = isExpired(veg.useBeforeDate);
              const daysRemaining = getDaysRemaining(veg.useBeforeDate);
              
              return (
                <tr 
                  key={veg.id} 
                  className={
                    expired || veg.spoiled ? 'table-danger' : 
                    daysRemaining <= 2 ? 'table-warning' : ''
                  }
                >
                  <td>{index + 1}</td>
                  <td className="fw-bold">
                    {veg.name}
                    {daysRemaining <= 2 && daysRemaining >= 0 && (
                      <span className="ms-2 text-warning">⚠️</span>
                    )}
                  </td>
                  <td>{veg.weight}</td>
                  <td>{veg.unit}</td>
                  <td>{formatDate(veg.addedDate)}</td>
                  <td>{formatDate(veg.useBeforeDate)}</td>
                  <td>
                    {getExpiryBadge(veg.useBeforeDate)}
                  </td>
                  {showActions && (
                    <td>
                      <div className="d-flex gap-2">
                        <Button
                          variant="outline-warning"
                          size="sm"
                          onClick={() => onEdit(veg)}
                          title="Edit"
                          disabled={expired || veg.spoiled}
                        >
                          ✏️
                        </Button>
                        <Button
                          variant="outline-danger"
                          size="sm"
                          onClick={() => onDelete(veg.id)}
                          title="Delete"
                        >
                          🗑️
                        </Button>
                      </div>
                    </td>
                  )}
                </tr>
              );
            })
          )}
        </tbody>
      </Table>
    </div>
  );
};

export default VegetableTable;