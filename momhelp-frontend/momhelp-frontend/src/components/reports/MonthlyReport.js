import React, { useState, useEffect } from 'react';
import { Container, Card, Row, Col, Form, Button, Table, Alert } from 'react-bootstrap';
import { BarChart, Bar, PieChart, Pie, Cell, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import LoadingSpinner from '../common/LoadingSpinner';
import api from '../../services/api';

const MonthlyReport = () => {
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [report, setReport] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884D8'];

  useEffect(() => {
    fetchReport();
  }, []);

  const fetchReport = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await api.get(
        `/usage-history/monthly-report?year=${year}&month=${month}`
      );
      setReport(response.data);
    } catch (err) {
      setError('Failed to load report');
    } finally {
      setLoading(false);
    }
  };

  const vegetableChartData = report?.vegetableUsageSummary 
    ? Object.entries(report.vegetableUsageSummary).map(([name, weight]) => ({
        name, value: weight
      }))
    : [];

  const dishChartData = report?.dishFrequency
    ? Object.entries(report.dishFrequency).map(([name, count]) => ({
        name, value: count
      }))
    : [];

  if (loading) return <LoadingSpinner message="Loading report..." />;

  return (
    <Container className="py-4">
      <Card className="shadow-lg">
        <Card.Header className="bg-primary text-white">
          <h4 className="mb-0">📊 Monthly Usage Report</h4>
        </Card.Header>

        <Card.Body>
          {/* Month/Year Selector */}
          <Row className="mb-4">
            <Col md={4}>
              <Form.Group>
                <Form.Label>Month</Form.Label>
                <Form.Select value={month} onChange={(e) => setMonth(Number(e.target.value))}>
                  {[...Array(12)].map((_, i) => (
                    <option key={i+1} value={i+1}>
                      {new Date(2000, i, 1).toLocaleString('default', { month: 'long' })}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={4}>
              <Form.Group>
                <Form.Label>Year</Form.Label>
                <Form.Select value={year} onChange={(e) => setYear(Number(e.target.value))}>
                  {[2024, 2025, 2026, 2027].map(y => (
                    <option key={y} value={y}>{y}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={4} className="d-flex align-items-end">
              <Button variant="primary" onClick={fetchReport} className="w-100">
                Load Report
              </Button>
            </Col>
          </Row>

          {error && <Alert variant="danger">{error}</Alert>}

          {report && (
            <>
              {/* Summary Cards */}
              <Row className="mb-4">
                <Col md={4}>
                  <Card className="text-center border-success">
                    <Card.Body>
                      <h3 className="text-success">{report.totalTransactions}</h3>
                      <p className="text-muted mb-0">Total Uses</p>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={4}>
                  <Card className="text-center border-info">
                    <Card.Body>
                      <h3 className="text-info">{report.totalWeightUsed?.toFixed(2)} Kg</h3>
                      <p className="text-muted mb-0">Total Weight Used</p>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={4}>
                  <Card className="text-center border-warning">
                    <Card.Body>
                      <h3 className="text-warning">{Object.keys(report.vegetableUsageSummary || {}).length}</h3>
                      <p className="text-muted mb-0">Vegetables Used</p>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>

              {/* Charts */}
              <Row className="mb-4">
                <Col md={6}>
                  <Card>
                    <Card.Header>Vegetable Usage</Card.Header>
                    <Card.Body>
                      <ResponsiveContainer width="100%" height={300}>
                        <PieChart>
                          <Pie
                            data={vegetableChartData}
                            cx="50%"
                            cy="50%"
                            labelLine={false}
                            label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                            outerRadius={80}
                            fill="#8884d8"
                            dataKey="value"
                          >
                            {vegetableChartData.map((entry, index) => (
                              <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                            ))}
                          </Pie>
                          <Tooltip />
                        </PieChart>
                      </ResponsiveContainer>
                    </Card.Body>
                  </Card>
                </Col>

                <Col md={6}>
                  <Card>
                    <Card.Header>Dish Frequency</Card.Header>
                    <Card.Body>
                      <ResponsiveContainer width="100%" height={300}>
                        <BarChart data={dishChartData}>
                          <CartesianGrid strokeDasharray="3 3" />
                          <XAxis dataKey="name" />
                          <YAxis />
                          <Tooltip />
                          <Bar dataKey="value" fill="#82ca9d" />
                        </BarChart>
                      </ResponsiveContainer>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>

              {/* History Table */}
              <Card>
                <Card.Header>Usage History</Card.Header>
                <Card.Body>
                  <Table striped bordered hover>
                    <thead>
                      <tr>
                        <th>Date</th>
                        <th>Vegetable</th>
                        <th>Weight Used</th>
                        <th>Dish</th>
                      </tr>
                    </thead>
                    <tbody>
                      {report.usageHistory?.map((item, idx) => (
                        <tr key={idx}>
                          <td>{new Date(item.usedDate).toLocaleDateString()}</td>
                          <td>{item.vegetableName}</td>
                          <td>{item.weightUsed} {item.unit}</td>
                          <td>{item.dishName || '-'}</td>
                        </tr>
                      ))}
                    </tbody>
                  </Table>
                </Card.Body>
              </Card>
            </>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default MonthlyReport;
