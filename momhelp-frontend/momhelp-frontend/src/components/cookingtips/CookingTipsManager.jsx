import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Button, Alert, Spinner, Tabs, Tab, Form, InputGroup } from 'react-bootstrap';
import { FaSearch } from 'react-icons/fa';
import CookingTipForm from './CookingTipForm';
import CookingTipCard from './CookingTipCard';
import CookingTipDetail from './CookingTipDetail';
import cookingTipService from '../../services/cookingTipService';

const CookingTipsManager = () => {
  const [tips, setTips] = useState([]);
  const [filteredTips, setFilteredTips] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [showDetail, setShowDetail] = useState(false);
  const [editingTip, setEditingTip] = useState(null);
  const [viewingTip, setViewingTip] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [activeTab, setActiveTab] = useState('all');
  const [searchQuery, setSearchQuery] = useState('');

  const userId = 1;

  useEffect(() => {
    loadTips();
  }, []);

  useEffect(() => {
    filterTips();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tips, activeTab, searchQuery]);

  const loadTips = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await cookingTipService.getAllTips(userId);
      if (response.data.success) {
        setTips(response.data.data);
      }
    } catch (err) {
      console.error('Error loading cooking tips:', err);
      setTips([]);
    } finally {
      setLoading(false);
    }
  };

  const filterTips = () => {
    let filtered = [...tips];

    if (searchQuery) {
      filtered = filtered.filter(tip =>
        tip.title.toLowerCase().includes(searchQuery.toLowerCase())
      );
    }

    switch (activeTab) {
      case 'favorites':
        filtered = filtered.filter(t => t.isFavorite);
        break;
      case 'beginner':
        filtered = filtered.filter(t => t.difficultyLevel === 'BEGINNER');
        break;
      case 'intermediate':
        filtered = filtered.filter(t => t.difficultyLevel === 'INTERMEDIATE');
        break;
      case 'advanced':
        filtered = filtered.filter(t => t.difficultyLevel === 'ADVANCED');
        break;
      case 'popular':
        filtered = [...filtered].sort((a, b) => b.viewCount - a.viewCount);
        break;
      case 'helpful':
        filtered = [...filtered].sort((a, b) => b.helpfulCount - a.helpfulCount);
        break;
      default:
        break;
    }

    setFilteredTips(filtered);
  };

  const handleAddTip = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await cookingTipService.addTip(formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        await loadTips();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error adding tip:', err);
      setError(err.response?.data?.message || 'Failed to add tip');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateTip = async (formData) => {
    try {
      setLoading(true);
      setError(null);
      const response = await cookingTipService.updateTip(editingTip.id, formData);
      if (response.data.success) {
        setSuccess(response.data.message);
        setShowForm(false);
        setEditingTip(null);
        await loadTips();
        setTimeout(() => setSuccess(null), 3000);
      }
    } catch (err) {
      console.error('Error updating tip:', err);
      setError(err.response?.data?.message || 'Failed to update tip');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (formData) => {
    if (editingTip) {
      handleUpdateTip(formData);
    } else {
      handleAddTip(formData);
    }
  };

  const handleView = async (tip) => {
    try {
      await cookingTipService.incrementViewCount(tip.id);
      setViewingTip({ ...tip, viewCount: tip.viewCount + 1 });
      setShowDetail(true);
      await loadTips();
    } catch (err) {
      console.error('Error updating view count:', err);
      setViewingTip(tip);
      setShowDetail(true);
    }
  };

  const handleEdit = (tip) => {
    setEditingTip(tip);
    setShowForm(true);
    setError(null);
    setSuccess(null);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this tip?')) {
      try {
        setLoading(true);
        setError(null);
        const response = await cookingTipService.deleteTip(id);
        if (response.data.success) {
          setSuccess(response.data.message);
          await loadTips();
          setTimeout(() => setSuccess(null), 3000);
        }
      } catch (err) {
        console.error('Error deleting tip:', err);
        setError(err.response?.data?.message || 'Failed to delete tip');
      } finally {
        setLoading(false);
      }
    }
  };

  const handleToggleFavorite = async (id) => {
    try {
      setLoading(true);
      setError(null);
      const response = await cookingTipService.toggleFavorite(id);
      if (response.data.success) {
        await loadTips();
        if (viewingTip && viewingTip.id === id) {
          setViewingTip(response.data.data);
        }
      }
    } catch (err) {
      console.error('Error toggling favorite:', err);
      setError('Failed to update favorite status');
    } finally {
      setLoading(false);
    }
  };

  const handleMarkHelpful = async (id) => {
    try {
      setLoading(true);
      setError(null);
      const response = await cookingTipService.incrementHelpfulCount(id);
      if (response.data.success) {
        await loadTips();
        if (viewingTip && viewingTip.id === id) {
          setViewingTip(response.data.data);
        }
        setSuccess(response.data.message);
        setTimeout(() => setSuccess(null), 2000);
      }
    } catch (err) {
      console.error('Error marking as helpful:', err);
      setError('Failed to mark as helpful');
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = () => {
    setShowForm(false);
    setEditingTip(null);
    setError(null);
    setSuccess(null);
  };

  const favoriteCount = tips.filter(t => t.isFavorite).length;

  if (loading && tips.length === 0) {
    return (
      <Container className="mt-4 text-center">
        <Spinner animation="border" variant="info" />
        <p className="mt-2">Loading cooking tips...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <Row className="mb-4">
        <Col>
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h2>💡 Cooking Tips & Tutorials</h2>
              <p className="text-muted">
                Learn helpful cooking techniques and tips
              </p>
            </div>
            <Button 
              variant="info"
              onClick={() => {
                setShowForm(!showForm);
                setEditingTip(null);
              }}
            >
              {showForm ? '📋 View Tips' : '➕ Add Tip'}
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

      {showForm ? (
        <CookingTipForm
          initialData={editingTip}
          onSubmit={handleSubmit}
          onCancel={handleCancel}
        />
      ) : (
        <>
          <Row className="mb-3">
            <Col md={6}>
              <InputGroup>
                <InputGroup.Text>
                  <FaSearch />
                </InputGroup.Text>
                <Form.Control
                  type="text"
                  placeholder="Search tips..."
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
              </InputGroup>
            </Col>
          </Row>

          <Tabs
            activeKey={activeTab}
            onSelect={(k) => setActiveTab(k)}
            className="mb-3"
          >
            <Tab eventKey="all" title={`All (${tips.length})`} />
            <Tab eventKey="favorites" title={`❤️ Favorites (${favoriteCount})`} />
            <Tab eventKey="beginner" title="🟢 Beginner" />
            <Tab eventKey="intermediate" title="🟡 Intermediate" />
            <Tab eventKey="advanced" title="🔴 Advanced" />
            <Tab eventKey="popular" title="🔥 Most Viewed" />
            <Tab eventKey="helpful" title="👍 Most Helpful" />
          </Tabs>

          {filteredTips.length === 0 ? (
            <p className="text-center text-muted">No tips found</p>
          ) : (
            <Row>
              {filteredTips.map(tip => (
                <Col key={tip.id} md={6} lg={4} className="mb-4">
                  <CookingTipCard
                    tip={tip}
                    onView={handleView}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    onToggleFavorite={handleToggleFavorite}
                  />
                </Col>
              ))}
            </Row>
          )}
        </>
      )}

      <CookingTipDetail
        tip={viewingTip}
        show={showDetail}
        onHide={() => setShowDetail(false)}
        onToggleFavorite={handleToggleFavorite}
        onMarkHelpful={handleMarkHelpful}
      />
    </Container>
  );
};

export default CookingTipsManager;
