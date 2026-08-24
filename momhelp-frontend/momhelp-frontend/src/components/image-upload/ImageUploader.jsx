import React, { useState, useRef } from 'react';
import { Button, Spinner } from 'react-bootstrap';

const ImageUploader = ({ onImageSelected, loading = false }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [error, setError] = useState(null);
  const fileInputRef = useRef(null);

  const handleFileSelect = (event) => {
    const file = event.target.files[0];
    if (file) {
      validateAndSetFile(file);
    }
  };

  const validateAndSetFile = (file) => {
    setError(null);

    // Validate file type
    const validTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp'];
    if (!validTypes.includes(file.type)) {
      setError('Invalid file type');
      return;
    }

    // Validate file size (5MB)
    const maxSize = 5 * 1024 * 1024;
    if (file.size > maxSize) {
      setError('File too large');
      return;
    }

    setSelectedFile(file);
    if (onImageSelected) {
      onImageSelected(file);
    }
  };

  const handleRemoveFile = () => {
    setSelectedFile(null);
    setError(null);
    if (fileInputRef.current) {
      fileInputRef.current.value = '';
    }
  };

  return (
    <div className="d-inline-flex align-items-center gap-2">
      {error && (
        <small className="text-danger">{error}</small>
      )}

      <input
        type="file"
        ref={fileInputRef}
        onChange={handleFileSelect}
        accept="image/jpeg,image/jpg,image/png,image/webp"
        className="d-none"
      />

      {selectedFile ? (
        <Button 
          variant="success" 
          size="sm"
          onClick={handleRemoveFile}
          disabled={loading}
          title={selectedFile.name}
        >
          {loading ? (
            <>
              <Spinner size="sm" /> Detecting...
            </>
          ) : (
            <>✅ {selectedFile.name.substring(0, 15)}{selectedFile.name.length > 15 ? '...' : ''}</>
          )}
        </Button>
      ) : (
        <Button 
          variant="outline-success" 
          size="sm"
          onClick={() => fileInputRef.current?.click()}
          disabled={loading}
        >
          📸 Auto-detect
        </Button>
      )}
    </div>
  );
};

export default ImageUploader;