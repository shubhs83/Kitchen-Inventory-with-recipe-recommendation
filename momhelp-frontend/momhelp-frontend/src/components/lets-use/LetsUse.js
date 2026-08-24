import React, { useState, useEffect } from 'react';
import { Container, Card, Form, Button, Alert, Row, Col, Table } from 'react-bootstrap';
import Select from 'react-select';
import { toast, ToastContainer } from 'react-toastify';
import LoadingSpinner from '../common/LoadingSpinner';
import api from '../../services/api';

const LetsUse = () => {
  const [vegetables, setVegetables] = useState([]);
  const [selectedVegetable, setSelectedVegetable] = useState(null);
  const [weightUsed, setWeightUsed] = useState('');
  const [unitUsed, setUnitUsed] = useState('Kg');   // ✅ NEW
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchVegetables();
  }, []);

  const fetchVegetables = async () => {
    try {
      setLoading(true);
      const response = await api.get('/vegetables/available');
      setVegetables(response.data);
    } catch (err) {
      setError('Failed to load vegetables');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const vegetableOptions = vegetables.map(veg => ({
    value: veg.id,
    label: `${veg.name} (${veg.weight} ${veg.unit} available)`,
    vegetable: veg
  }));

  const handleUseVegetable = async (e) => {
    e.preventDefault();

    if (!selectedVegetable) {
      toast.error('Please select a vegetable');
      return;
    }

    if (!weightUsed || parseFloat(weightUsed) <= 0) {
      toast.error('Please enter valid weight');
      return;
    }

    try {
      setSubmitting(true);

      const response = await api.put(
        `/vegetables/use/${selectedVegetable.value}`,
        {
          vegetableId: selectedVegetable.value,
          weightUsed: parseFloat(weightUsed),
          unitUsed: unitUsed          // ✅ SEND UNIT
        }
      );

      if (response.data.success) {
        toast.success(response.data.message);
        setSelectedVegetable(null);
        setWeightUsed('');
        setUnitUsed('Kg');
        fetchVegetables();
      }
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to update vegetable');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <LoadingSpinner message="Loading vegetables..." />;

  return (
    <Container className="py-4">
      <ToastContainer position="top-right" autoClose={3000} />

      <Card className="shadow-lg">
        <Card.Header className="bg-secondary text-white">
          <h4 className="mb-0">🛒 Let's Use - Update Vegetable Quantity</h4>
          <small>Reduce vegetable weight after cooking</small>
        </Card.Header>

        <Card.Body>
          {error && <Alert variant="danger">{error}</Alert>}

          <Card className="mb-4 border-secondary">
            <Card.Body>
              <Form onSubmit={handleUseVegetable}>
                <Row>
                  <Col md={5}>
                    <Form.Group className="mb-3">
                      <Form.Label className="fw-bold">
                        Select Vegetable *
                      </Form.Label>
                      <Select
                        options={vegetableOptions}
                        value={selectedVegetable}
                        onChange={setSelectedVegetable}
                        placeholder="Search and select vegetable..."
                        isSearchable
                        isClearable
                      />
                    </Form.Group>
                  </Col>

                  <Col md={3}>
                    <Form.Group className="mb-3">
                      <Form.Label className="fw-bold">
                        Weight Used *
                      </Form.Label>
                      <Form.Control
                        type="number"
                        step="0.01"
                        value={weightUsed}
                        onChange={(e) => setWeightUsed(e.target.value)}
                        placeholder="0.00"
                        disabled={!selectedVegetable}
                      />
                    </Form.Group>
                  </Col>

                  {/* ✅ NEW UNIT DROPDOWN */}
                  <Col md={2}>
                    <Form.Group className="mb-3">
                      <Form.Label className="fw-bold">
                        Unit *
                      </Form.Label>
                      <Form.Select
                        value={unitUsed}
                        onChange={(e) => setUnitUsed(e.target.value)}
                      >
                        <option value="Kg">Kg</option>
                        <option value="Gm">Gm</option>
                        <option value="Piece">Piece</option>
                        <option value="Bundle">Bundle</option>
                      </Form.Select>
                    </Form.Group>
                  </Col>

                  <Col md={2} className="d-flex align-items-end">
                    <Button
                      type="submit"
                      variant="secondary"
                      className="w-100 mb-3"
                      disabled={submitting}
                    >
                      {submitting ? 'Using...' : '✓ Use'}
                    </Button>
                  </Col>
                </Row>
              </Form>
            </Card.Body>
          </Card>

          {/* TABLE */}
          <Card>
            <Card.Header className="bg-light">
              <h6 className="mb-0">📦 Available Vegetables ({vegetables.length})</h6>
            </Card.Header>
            <Card.Body>
              <Table striped hover>
                <thead>
                  <tr>
                    <th>#</th>
                    <th>Vegetable</th>
                    <th>Available Weight</th>
                    <th>Unit</th>
                    <th>Use Before</th>
                  </tr>
                </thead>
                <tbody>
                  {vegetables.map((veg, index) => (
                    <tr key={veg.id}>
                      <td>{index + 1}</td>
                      <td className="fw-bold">{veg.name}</td>
                      <td>{veg.weight}</td>
                      <td>{veg.unit}</td>
                      <td>{new Date(veg.useBeforeDate).toLocaleDateString('en-IN')}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </Card.Body>
          </Card>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default LetsUse;
