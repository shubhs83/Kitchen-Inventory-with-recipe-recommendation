import React, { useState, useEffect } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ImageUploader from '../image-upload/ImageUploader';
import imageDetectionService from '../../services/imageDetectionService';
import { toast } from 'react-toastify';

const VegetableForm = ({ initialData = {}, onSubmit, onCancel, isEdit = false }) => {
  const [formData, setFormData] = useState({
    name: '',
    weight: '',
    unit: 'Kg',
    addedDate: new Date(),
    useBeforeDate: new Date(new Date().setDate(new Date().getDate() + 7)),
  });

  const [errors, setErrors] = useState({});
  const [selectedImage, setSelectedImage] = useState(null);
  const [detectingImage, setDetectingImage] = useState(false);

  useEffect(() => {
    if (initialData && Object.keys(initialData).length > 0) {
      setFormData({
        name: initialData.name || '',
        weight: initialData.weight || '',
        unit: initialData.unit || 'Kg',
        addedDate: initialData.addedDate ? new Date(initialData.addedDate) : new Date(),
        useBeforeDate: initialData.useBeforeDate ? new Date(initialData.useBeforeDate) : new Date(new Date().setDate(new Date().getDate() + 7)),
      });
    }
  }, [initialData]);

  const handleImageSelected = async (file) => {
    setSelectedImage(file);
    
    // Auto-detect vegetable from image
    try {
      setDetectingImage(true);
      const response = await imageDetectionService.detectVegetables(file);
      
      if (response.data.success && response.data.detectedVegetables.length > 0) {
        const topVegetable = response.data.detectedVegetables[0];
        setFormData(prev => ({
          ...prev,
          name: topVegetable.name
        }));
        toast.success(`Detected: ${topVegetable.name}`);
      }
    } catch (err) {
      console.error('Error detecting vegetable:', err);
    } finally {
      setDetectingImage(false);
    }
  };

  const validateForm = () => {
    const newErrors = {};
    
    if (!formData.name.trim()) {
      newErrors.name = 'Vegetable name is required';
    }
    
    if (!formData.weight || isNaN(formData.weight) || parseFloat(formData.weight) <= 0) {
      newErrors.weight = 'Please enter a valid weight';
    }
    
    if (!formData.unit) {
      newErrors.unit = 'Please select a unit';
    }
    
    if (!formData.addedDate) {
      newErrors.addedDate = 'Added date is required';
    }
    
    if (!formData.useBeforeDate) {
      newErrors.useBeforeDate = 'Use before date is required';
    }
    
    if (formData.useBeforeDate < formData.addedDate) {
      newErrors.useBeforeDate = 'Use before date must be after added date';
    }
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const handleDateChange = (date, field) => {
    setFormData(prev => ({
      ...prev,
      [field]: date
    }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      const dataToSubmit = {
        ...formData,
        weight: parseFloat(formData.weight),
        addedDate: formData.addedDate.toISOString().split('T')[0],
        useBeforeDate: formData.useBeforeDate.toISOString().split('T')[0]
      };
      onSubmit(dataToSubmit);
    }
  };

  return (
    <Card className="shadow">
      <Card.Body>
        <div className="d-flex justify-content-between align-items-center mb-4">
          <Card.Title className="mb-0">{isEdit ? 'Update Vegetable' : 'Add New Vegetable'}</Card.Title>
          {!isEdit && (
            <ImageUploader
              onImageSelected={handleImageSelected}
              loading={detectingImage}
            />
          )}
        </div>
        
        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Vegetable Name *</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  placeholder="Enter vegetable name"
                  isInvalid={!!errors.name}
                  disabled={detectingImage}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.name}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            
            <Col md={3}>
              <Form.Group>
                <Form.Label>Weight *</Form.Label>
                <Form.Control
                  type="number"
                  step="0.01"
                  name="weight"
                  value={formData.weight}
                  onChange={handleInputChange}
                  placeholder="0.00"
                  isInvalid={!!errors.weight}
                />
                <Form.Control.Feedback type="invalid">
                  {errors.weight}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
            
            <Col md={3}>
              <Form.Group>
                <Form.Label>Unit *</Form.Label>
                <Form.Select
                  name="unit"
                  value={formData.unit}
                  onChange={handleInputChange}
                  isInvalid={!!errors.unit}
                >
                  <option value="Kg">Kg</option>
                  <option value="Gm">Gm</option>
                  <option value="Piece">Piece</option>
                  <option value="Bundle">Bundle</option>
                </Form.Select>
                <Form.Control.Feedback type="invalid">
                  {errors.unit}
                </Form.Control.Feedback>
              </Form.Group>
            </Col>
          </Row>
          
          <Row className="mb-4">
            <Col md={6}>
              <Form.Group>
                <Form.Label>Date Added *</Form.Label>
                <DatePicker
                  selected={formData.addedDate}
                  onChange={(date) => handleDateChange(date, 'addedDate')}
                  dateFormat="dd-MMM-yyyy"
                  className="form-control"
                  isInvalid={!!errors.addedDate}
                />
                {errors.addedDate && (
                  <div className="text-danger small mt-1">{errors.addedDate}</div>
                )}
              </Form.Group>
            </Col>
            
            <Col md={6}>
              <Form.Group>
                <Form.Label>Use Before Date *</Form.Label>
                <DatePicker
                  selected={formData.useBeforeDate}
                  onChange={(date) => handleDateChange(date, 'useBeforeDate')}
                  dateFormat="dd-MMM-yyyy"
                  className="form-control"
                  minDate={formData.addedDate}
                  isInvalid={!!errors.useBeforeDate}
                />
                {errors.useBeforeDate && (
                  <div className="text-danger small mt-1">{errors.useBeforeDate}</div>
                )}
              </Form.Group>
            </Col>
          </Row>
          
          <div className="d-flex justify-content-end gap-2">
            <Button variant="secondary" onClick={onCancel}>
              Cancel
            </Button>
            <Button variant={isEdit ? "warning" : "success"} type="submit" disabled={detectingImage}>
              {detectingImage ? 'Detecting...' : (isEdit ? 'Update' : 'Add Vegetable')}
            </Button>
          </div>
        </Form>
      </Card.Body>
    </Card>
  );
};

export default VegetableForm;