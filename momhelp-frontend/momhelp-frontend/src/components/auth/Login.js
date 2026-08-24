import React, { useState } from 'react';
import { Container, Card, Form, Button, Alert } from 'react-bootstrap';
import { useNavigate, Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import authService from '../../services/authService';

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    usernameOrEmail: '',
    password: ''
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const response = await authService.login(formData);
      
      if (response.data.success) {
        const { token, userId, username, email, fullName } = response.data.data;
        
        // Store token and user info
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify({
          userId,
          username,
          email,
          fullName
        }));

        toast.success('Login successful!');
        navigate('/'); // Redirect to dashboard
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed. Please try again.');
      toast.error('Login failed!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="py-5">
      <div className="row justify-content-center">
        <div className="col-md-6 col-lg-5">
          <Card className="shadow-lg">
            <Card.Header className="bg-success text-white text-center">
              <h4 className="mb-0">🔐 Login</h4>
              <small>May I Help You...Mom!</small>
            </Card.Header>
            <Card.Body className="p-4">
              {error && (
                <Alert variant="danger" dismissible onClose={() => setError(null)}>
                  {error}
                </Alert>
              )}

              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="login-username-or-email">
                  <Form.Label>Username or Email *</Form.Label>
                  <Form.Control
                    type="text"
                    name="usernameOrEmail"
                    value={formData.usernameOrEmail}
                    onChange={handleChange}
                    placeholder="Enter username or email"
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="login-password">
                  <Form.Label>Password *</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter password"
                    required
                  />
                </Form.Group>

                <Button
                  variant="success"
                  type="submit"
                  className="w-100 mb-3"
                  disabled={loading}
                >
                  {loading ? 'Logging in...' : '🔓 Login'}
                </Button>

                <div className="text-center">
                  <small>
                    Don't have an account?{' '}
                    <Link to="/signup" className="text-success fw-bold">
                      Sign Up
                    </Link>
                  </small>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </div>
      </div>
    </Container>
  );
};

export default Login;
