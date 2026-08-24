import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner } from 'react-bootstrap';
import ShoppingListForm from './ShoppingListForm';
import ShoppingListView from './ShoppingListView';
import shoppingListService from '../../services/shoppingListService';

const ShoppingListManager = () => {
  const [items, setItems] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingItem, setEditingItem] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const userId = 1;

  useEffect(() => {
    loadItems();
  }, []);

  const loadItems = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await shoppingListService.getAllItems(userId);
      console.log('Load items response:', response);
      if (response.data.success) {
        setItems(response.data.data);
      }
    } catch (err) {
      console.error('Error loading shopping list:', err);
      setItems([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAddItem = async (formData) => {
    console.log('handleAddItem called with:', formData);
    try {
      setLoading(true);
      setError(null);
      const response = await shoppingListService.addItem(formData);
      console.log('Add item response:', response);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        await loadItems();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error adding item:', err);
      setError(err.response?.data?.message || 'Failed to add item');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateItem = async (formData) => {
    console.log('handleUpdateItem called with:', formData);
    try {
      setLoading(true);
      setError(null);
      const response = await shoppingListService.updateItem(editingItem.id, formData);
      console.log('Update item response:', response);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        setEditingItem(null);
        await loadItems();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error updating item:', err);
      setError(err.response?.data?.message || 'Failed to update item');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (formData) => {
    console.log('handleSubmit called, editingItem:', editingItem);
    if (editingItem) {
      handleUpdateItem(formData);
    } else {
      handleAddItem(formData);
    }
  };

  const handleEdit = (item) => {
    setEditingItem(item);
    setShowForm(true);
    setError(null);
    setSuccess(null);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      try {
        setLoading(true);
        setError(null);
        const response = await shoppingListService.deleteItem(id);
        if (response.data.success) {
          setSuccess(response.data.message);
          await loadItems();
          setTimeout(() => setSuccess(null), 3000);
        }
      } catch (err) {
        console.error('Error deleting item:', err);
        setError(err.response?.data?.message || 'Failed to delete item');
      } finally {
        setLoading(false);
      }
    }
  };

  const handleTogglePurchase = async (id, currentStatus) => {
    try {
      setLoading(true);
      setError(null);
      const response = currentStatus 
        ? await shoppingListService.markAsUnpurchased(id)
        : await shoppingListService.markAsPurchased(id);
      
      if (response.data.success) {
        await loadItems();
      }
    } catch (err) {
      console.error('Error toggling purchase status:', err);
      setError('Failed to update purchase status');
    } finally {
      setLoading(false);
    }
  };

  const handleClearPurchased = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await shoppingListService.clearPurchasedItems(userId);
      if (response.data.success) {
        setSuccess(response.data.message);
        await loadItems();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error clearing purchased items:', err);
      setError('Failed to clear purchased items');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingItem(null);
    setError(null);
    setSuccess(null);
  };

  if (loading && items.length === 0) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="success" />
        <p className="mt-2">Loading shopping list...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2>🛒 Shopping List</h2>
              <p className="text-muted">
                Manage your grocery shopping list and track purchases
              </p>
            </div>
            <Button 
              variant="success"
              onClick={() => {
                console.log('Add Item button clicked');
                setShowForm(!showForm);
                setEditingItem(null);
              }}
            >
              {showForm ? '📋 View List' : '➕ Add Item'}
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
            <ShoppingListForm
              initialData={editingItem}
              onSubmit={handleSubmit}
              onCancel={handleCancel}
            />
          ) : (
            <ShoppingListView
              items={items}
              onEdit={handleEdit}
              onDelete={handleDelete}
              onTogglePurchase={handleTogglePurchase}
              onClearPurchased={handleClearPurchased}
            />
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default ShoppingListManager;