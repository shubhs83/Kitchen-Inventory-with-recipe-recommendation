import React, { useState, useEffect } from 'react';
import { Container, Card, Table, Button, Badge, Alert, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoadingSpinner from '../common/LoadingSpinner';
import api from '../../services/api';

const SpoiledVegetables = () => {
  const [spoiledVegetables, setSpoiledVegetables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [vegetableToDelete, setVegetableToDelete] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchSpoiledVegetables();
  }, []);

  const fetchSpoiledVegetables = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await api.get('/vegetables/spoiled');
      setSpoiledVegetables(response.data);
    } catch (err) {
      console.error('Error fetching spoiled vegetables:', err);
      setError('Failed to load spoiled vegetables. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleRemoveClick = (vegetable) => {
    setVegetableToDelete(vegetable);
    setShowDeleteModal(true);
  };

  const confirmRemove = async () => {
    if (!vegetableToDelete) return;

    try {
      const response = await api.delete(
        `/vegetables/remove-spoiled/${vegetableToDelete.id}`
      );

      if (response.data.success) {
        toast.success(response.data.message || 'Spoiled vegetable removed successfully!');
        fetchSpoiledVegetables(); // Refresh list
      }
    } catch (err) {
      console.error('Error removing vegetable:', err);
      toast.error(err.response?.data?.message || 'Failed to remove vegetable');
    } finally {
      setShowDeleteModal(false);
      setVegetableToDelete(null);
    }
  };

  const formatDate = (dateString) => {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-IN', {
        day: '2-digit',
        month: 'short',
        year: 'numeric'
      });
    } catch {
      return dateString;
    }
  };

  const handleBack = () => {
    navigate('/');
  };

  if (loading) {
    return <LoadingSpinner message="Loading spoiled vegetables..." />;
  }

  return (
    <Container className="py-4">
      <ToastContainer position="top-right" autoClose={3000} />

      <Card className="shadow-lg">
        <Card.Header className="bg-danger text-white">
          <h4 className="mb-0">⚠️ Expired Vegetables</h4>
          <small>Vegetables that have passed their use-before date</small>
        </Card.Header>

        <Card.Body>
          {/* Info Alert */}
          <Alert variant="warning" className="mb-4">
            <div className="d-flex align-items-center">
              <span className="fs-3 me-3">🗑️</span>
              <div>
                <strong>Important:</strong> These vegetables are not safe to use.
                <br />
                <small>Please remove them from your inventory to keep track of fresh vegetables only.</small>
              </div>
            </div>
          </Alert>

          {/* Error Alert */}
          {error && (
            <Alert variant="danger" dismissible onClose={() => setError(null)}>
              {error}
            </Alert>
          )}

          {/* Empty State */}
          {spoiledVegetables.length === 0 ? (
            <Alert variant="success" className="text-center py-5">
              <div className="mb-3" style={{ fontSize: '4rem' }}>✅</div>
              <h4>Great! No Expired Vegetables!</h4>
              <p className="mb-3">All your vegetables are fresh and within their use-before dates.</p>
              <Button variant="success" onClick={handleBack}>
                ← Back to Dashboard
              </Button>
            </Alert>
          ) : (
            <>
              {/* Summary */}
              <div className="mb-4 p-3 bg-light rounded">
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <h5 className="mb-0 text-danger">
                      {spoiledVegetables.length} Expired Vegetable{spoiledVegetables.length !== 1 ? 's' : ''}
                    </h5>
                    <small className="text-muted">Click "Remove" to delete from inventory</small>
                  </div>
                  <Button variant="outline-secondary" onClick={handleBack}>
                    ← Back
                  </Button>
                </div>
              </div>

              {/* Table */}
              <div className="table-responsive">
                <Table striped bordered hover>
                  <thead className="table-danger">
                    <tr>
                      <th>#</th>
                      <th>Date Added</th>
                      <th>Vegetable Name</th>
                      <th>Weight</th>
                      <th>Unit</th>
                      <th>Use Before</th>
                      <th>Status</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    {spoiledVegetables.map((veg, index) => (
                      <tr key={veg.id} className="align-middle">
                        <td className="fw-bold">{index + 1}</td>
                        <td>{formatDate(veg.addedDate)}</td>
                        <td className="fw-bold text-danger">{veg.name}</td>
                        <td>{veg.weight}</td>
                        <td>{veg.unit}</td>
                        <td>
                          <span className="text-danger">
                            {formatDate(veg.useBeforeDate)}
                          </span>
                        </td>
                        <td>
                          <Badge bg="danger" className="px-3 py-2">
                            ❌ Expired
                          </Badge>
                        </td>
                        <td>
                          <Button
                            variant="danger"
                            size="sm"
                            onClick={() => handleRemoveClick(veg)}
                          >
                            🗑️ Remove
                          </Button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
              </div>

              {/* Bottom Actions */}
              <div className="d-flex justify-content-between align-items-center mt-4 pt-3 border-top">
                <Button variant="secondary" onClick={handleBack}>
                  ← Back to Dashboard
                </Button>
                <div className="text-muted">
                  <small>Total Expired: {spoiledVegetables.length}</small>
                </div>
              </div>
            </>
          )}
        </Card.Body>
      </Card>

      {/* Delete Confirmation Modal */}
      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)} centered>
        <Modal.Header closeButton className="bg-danger text-white">
          <Modal.Title>⚠️ Confirm Removal</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {vegetableToDelete && (
            <div>
              <p className="mb-3">
                Are you sure you want to remove this expired vegetable from your inventory?
              </p>
              <div className="p-3 bg-light rounded">
                <strong className="text-danger">{vegetableToDelete.name}</strong>
                <br />
                <small className="text-muted">
                  Weight: {vegetableToDelete.weight} {vegetableToDelete.unit}
                  <br />
                  Expired on: {formatDate(vegetableToDelete.useBeforeDate)}
                </small>
              </div>
              <Alert variant="warning" className="mt-3 mb-0">
                <small>
                  <strong>Note:</strong> This action cannot be undone. The vegetable will be permanently deleted.
                </small>
              </Alert>
            </div>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={confirmRemove}>
            🗑️ Yes, Remove It
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default SpoiledVegetables;
