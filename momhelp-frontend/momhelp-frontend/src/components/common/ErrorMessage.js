import React from 'react';
import { Alert } from 'react-bootstrap';

const ErrorMessage = ({ message, variant = 'danger', onClose }) => {
  return (
    <Alert variant={variant} dismissible={!!onClose} onClose={onClose}>
      <Alert.Heading>Error!</Alert.Heading>
      <p>{message}</p>
    </Alert>
  );
};

export default ErrorMessage;