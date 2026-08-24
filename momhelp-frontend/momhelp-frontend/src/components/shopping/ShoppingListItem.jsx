import React from 'react';
import { Card, Badge, Button, Form } from 'react-bootstrap';
import { FaEdit, FaTrash } from 'react-icons/fa';

const ShoppingListItem = ({ item, onEdit, onDelete, onTogglePurchase }) => {
  const getPriorityColor = (priority) => {
    const colors = {
      HIGH: 'danger',
      MEDIUM: 'warning',
      LOW: 'info'
    };
    return colors[priority] || 'secondary';
  };

  const getCategoryIcon = (category) => {
    const icons = {
      Vegetables: '🥬',
      Fruits: '🍎',
      Dairy: '🥛',
      Grains: '🌾',
      Spices: '🌶️',
      Meat: '🍖',
      Seafood: '🐟',
      Snacks: '🍪',
      Beverages: '☕',
      Condiments: '🧂',
      Others: '📦'
    };
    return icons[category] || '📦';
  };

  return (
    <Card className={`mb-3 shadow-sm ${item.isPurchased ? 'bg-light text-muted' : ''}`}>
      <Card.Body>
        <div className="d-flex justify-content-between">
          <div className="flex-grow-1">
            <div className="d-flex gap-2 align-items-center mb-2">
              <Form.Check
                type="checkbox"
                checked={item.isPurchased}
                onChange={() => onTogglePurchase(item.id, item.isPurchased)}
              />
              <h5 className={item.isPurchased ? 'text-decoration-line-through' : ''}>
                {getCategoryIcon(item.category)} {item.itemName}
              </h5>
            </div>

            <div className="d-flex gap-2 mb-2 flex-wrap">
              <Badge bg={getPriorityColor(item.priority)}>{item.priority}</Badge>
              <Badge bg="secondary">{item.category}</Badge>
              <Badge bg="info">{item.quantity} {item.unit}</Badge>
              {item.estimatedPrice > 0 && (
                <Badge bg="success">₹{item.estimatedPrice.toFixed(2)}</Badge>
              )}
            </div>

            {item.notes && <small className="text-muted">{item.notes}</small>}
          </div>

          <div className="d-flex gap-2">
            <Button
              size="sm"
              variant="outline-primary"
              onClick={() => onEdit(item)}
              disabled={item.isPurchased}
            >
              <FaEdit />
            </Button>
            <Button
              size="sm"
              variant="outline-danger"
              onClick={() => onDelete(item.id)}
            >
              <FaTrash />
            </Button>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

export default ShoppingListItem;
