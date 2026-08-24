import React from 'react';
import { Spinner } from 'react-bootstrap';

const LoadingSpinner = ({ message = 'Loading...' }) => {
  return (
    <div className="text-center my-5">
      <Spinner animation="border" role="status" variant="success">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
      {message && <p className="mt-2">{message}</p>}
    </div>
  );
};

export default LoadingSpinner;