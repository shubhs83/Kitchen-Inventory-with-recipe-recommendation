import React from 'react';
import { Button, Spinner } from 'react-bootstrap';
import useSpeechRecognition from '../../hooks/useSpeechRecognition';

const VoiceButton = ({ onTranscript, variant = 'outline-primary', size = 'sm', className = '' }) => {
  const {
    isListening,
    transcript,
    interimTranscript,
    isSupported,
    startListening,
    stopListening
  } = useSpeechRecognition();

  // Send transcript to parent when speech ends
  React.useEffect(() => {
    if (transcript && !isListening) {
      onTranscript(transcript);
    }
  }, [transcript, isListening, onTranscript]);

  const handleClick = () => {
    if (isListening) {
      stopListening();
    } else {
      startListening();
    }
  };

  if (!isSupported) {
    return null; // Don't show button if not supported
  }

  return (
    <div className="voice-button-container">
      <Button
        variant={isListening ? 'danger' : variant}
        size={size}
        onClick={handleClick}
        className={`voice-button ${className}`}
        title={isListening ? 'Click to stop listening' : 'Click to speak'}
      >
        {isListening ? (
          <>
            <Spinner
              as="span"
              animation="grow"
              size="sm"
              role="status"
              className="me-2"
            />
            Listening...
          </>
        ) : (
          <>
            🎤 Voice
          </>
        )}
      </Button>
      
      {(isListening && interimTranscript) && (
        <div className="voice-interim-text mt-2 p-2 bg-light rounded">
          <small className="text-muted">You said: {interimTranscript}</small>
        </div>
      )}
    </div>
  );
};

export default VoiceButton;