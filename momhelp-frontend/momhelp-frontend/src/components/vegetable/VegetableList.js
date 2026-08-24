import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Form, Modal, Alert } from 'react-bootstrap';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import vegetableService from '../../services/vegetableService';
import VegetableForm from './VegetableForm';
import VegetableTable from './VegetableTable';
import LoadingSpinner from '../common/LoadingSpinner';
import VoiceButton from '../voice/VoiceButton';

const VegetableList = () => {
  const [vegetables, setVegetables] = useState([]);
  const [filteredVegetables, setFilteredVegetables] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [selectedVegetable, setSelectedVegetable] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [vegetableToDelete, setVegetableToDelete] = useState(null);

  // Fetch vegetables on component mount
  useEffect(() => {
    fetchVegetables();
  }, []);

  // Filter vegetables when search term changes
  useEffect(() => {
    if (searchTerm.trim() === '') {
      setFilteredVegetables(vegetables);
    } else {
      const filtered = vegetables.filter(veg =>
        veg.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
      setFilteredVegetables(filtered);
    }
  }, [searchTerm, vegetables]);

  const fetchVegetables = async () => {
    try {
      setLoading(true);
      setError(null);
      // CHANGED: Use available vegetables endpoint instead of all vegetables
      const response = await vegetableService.getAvailableVegetables();
      setVegetables(response.data);
      setFilteredVegetables(response.data);
    } catch (err) {
      setError('Failed to load vegetables. Please try again.');
      console.error('Error fetching vegetables:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddVegetable = async (vegetableData) => {
    try {
      const response = await vegetableService.addVegetable(vegetableData);
      if (response.data.success) {
        toast.success(response.data.message);
        setShowForm(false);
        fetchVegetables();
      }
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to add vegetable');
    }
  };

  const handleUpdateVegetable = async (vegetableData) => {
    try {
      const response = await vegetableService.updateVegetable(selectedVegetable.id, vegetableData);
      if (response.data.success) {
        toast.success(response.data.message);
        setShowForm(false);
        setSelectedVegetable(null);
        fetchVegetables();
      }
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to update vegetable');
    }
  };

  const handleDelete = (id) => {
    setVegetableToDelete(id);
    setShowDeleteModal(true);
  };

  const confirmDelete = async () => {
    try {
      const response = await vegetableService.deleteVegetable(vegetableToDelete);
      if (response.data.success) {
        toast.success(response.data.message);
        fetchVegetables();
      }
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to delete vegetable');
    } finally {
      setShowDeleteModal(false);
      setVegetableToDelete(null);
    }
  };

  const handleEdit = (vegetable) => {
    setSelectedVegetable(vegetable);
    setShowForm(true);
  };

  const handleFormSubmit = (data) => {
    if (selectedVegetable) {
      handleUpdateVegetable(data);
    } else {
      handleAddVegetable(data);
    }
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setSelectedVegetable(null);
  };

  if (loading) {
    return <LoadingSpinner message="Loading vegetables..." />;
  }

  return (
    <Container className="py-4">
      <ToastContainer position="top-right" autoClose={3000} />

      <h2 className="mb-4">Manage Vegetables</h2>

      {error && (
        <Alert variant="danger" className="mb-4">
          {error}
        </Alert>
      )}

      {!showForm ? (
        <>
          {/* SEARCH ROW WITH VOICE BUTTON (ONLY ADDITION) */}
          <Row className="mb-4 align-items-end">
            <Col md={7}>
              <Form.Group>
                <Form.Label>Search Vegetables</Form.Label>
                <div className="d-flex gap-2">
                  <Form.Control
                    type="text"
                    placeholder="Search by name..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                  <VoiceButton
                    onTranscript={(text) => setSearchTerm(text)}
                    variant="outline-success"
                  />
                </div>
              </Form.Group>
            </Col>

            <Col md={5} className="text-end">
              <Button
                variant="success"
                onClick={() => setShowForm(true)}
                className="px-4"
              >
                + Add Vegetable
              </Button>
            </Col>
          </Row>

          <div className="mb-3">
            <span className="text-muted">
              Showing {filteredVegetables.length} of {vegetables.length} vegetables
            </span>
          </div>

          <VegetableTable
            vegetables={filteredVegetables}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />
        </>
      ) : (
        <VegetableForm
          initialData={selectedVegetable || {}}
          onSubmit={handleFormSubmit}
          onCancel={handleFormCancel}
          isEdit={!!selectedVegetable}
        />
      )}

      {/* Delete Confirmation Modal */}
      <Modal show={showDeleteModal} onHide={() => setShowDeleteModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Delete</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to delete this vegetable? This action cannot be undone.
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={confirmDelete}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default VegetableList;
