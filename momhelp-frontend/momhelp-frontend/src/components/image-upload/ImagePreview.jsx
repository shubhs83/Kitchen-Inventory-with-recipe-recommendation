import React from 'react';
import { Card, Button } from 'react-bootstrap';

const ImagePreview = ({ file, onRemove }) => {
  const [preview, setPreview] = React.useState(null);

  React.useEffect(() => {
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  }, [file]);

  if (!file || !preview) return null;

  return (
    <Card className="mt-3">
      <Card.Body>
        <div className="d-flex align-items-start gap-3">
          <div style={{ width: '150px', height: '150px', overflow: 'hidden', borderRadius: '8px' }}>
            <img
              src={preview}
              alt="Preview"
              style={{
                width: '100%',
                height: '100%',
                objectFit: 'cover'
              }}
            />
          </div>
          <div className="flex-grow-1">
            <h6 className="mb-2">{file.name}</h6>
            <p className="text-muted small mb-2">
              Size: {(file.size / 1024).toFixed(2)} KB
              <br />
              Type: {file.type}
            </p>
            <Button variant="outline-danger" size="sm" onClick={onRemove}>
              🗑️ Remove
            </Button>
          </div>
        </div>
      </Card.Body>
    </Card>
  );
};

export default ImagePreview;