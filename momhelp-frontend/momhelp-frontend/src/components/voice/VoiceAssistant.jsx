import React, { useState, useEffect } from 'react';
import { 
  Card, 
  Button, 
  Alert, 
  ListGroup, 
  Badge, 
  Modal, 
  Row, 
  Col 
} from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import useSpeechRecognition from '../../hooks/useSpeechRecognition';
import { processVoiceCommand, speak, getVoiceCommandsHelp } from '../../utils/voiceCommands';


const VoiceAssistant = () => {
  const navigate = useNavigate();
  const {
    isListening,
    transcript,
    interimTranscript,
    isSupported,
    startListening,
    stopListening,
    resetTranscript
  } = useSpeechRecognition();

  const [lastCommand, setLastCommand] = useState(null);
  const [showHelp, setShowHelp] = useState(false);

  // Process voice command when transcript is received
  React.useEffect(() => {
    if (transcript && !isListening) {
      handleVoiceCommand(transcript);
    }
  }, [transcript, isListening]);

  const handleVoiceCommand = (voiceTranscript) => {
    const result = processVoiceCommand(voiceTranscript);
    setLastCommand({ transcript: voiceTranscript, result });

    switch (result.action) {
      case 'navigate':
        speak(`Navigating to ${result.path}`);
        toast.success(`Going to ${result.path}`);
        setTimeout(() => navigate(result.path), 1000);
        break;

      case 'search':
        speak(`Searching for ${result.query}`);
        toast.info(`Searching for: ${result.query}`);
        // Implement search logic here
        break;

      case 'recipe':
        if (result.vegetables.length > 0) {
          speak(`Creating recipe with ${result.vegetables.join(', ')}`);
          toast.success(`Recipe with: ${result.vegetables.join(', ')}`);
          navigate('/ai-recipe-generator', { state: { vegetables: result.vegetables } });
        } else {
          speak('I could not detect any vegetables. Please try again.');
          toast.warning('No vegetables detected');
        }
        break;

      case 'unknown':
        speak('I did not understand that command. Please try again or say help for available commands.');
        toast.warning('Command not recognized. Say "help" for available commands.');
        break;

      default:
        break;
    }

    resetTranscript();
  };

  const handleStartListening = () => {
    speak('Yes, I am listening');
    startListening();
  };

  if (!isSupported) {
    return (
      <Card className="shadow-lg m-4">
        <Card.Body>
          <Alert variant="warning">
            <h5>Voice Assistant Not Supported</h5>
            <p>Your browser does not support voice recognition. Please use Chrome, Edge, or Safari.</p>
          </Alert>
        </Card.Body>
      </Card>
    );
  }

  const commandsHelp = getVoiceCommandsHelp();

  return (
    <div className="container py-4">
      <Card className="shadow-lg">
        <Card.Header className="bg-primary text-white">
          <div className="d-flex justify-content-between align-items-center">
            <div>
              <h4 className="mb-0">🎤 Voice Assistant</h4>
              <small>Control the app with your voice</small>
            </div>
            <Badge bg="light" text="dark" className="px-3 py-2">
              {isListening ? '🔴 Listening...' : '⚪ Ready'}
            </Badge>
          </div>
        </Card.Header>

        <Card.Body>
          {/* Info Alert */}
          <Alert variant="info" className="mb-4">
            <div className="d-flex align-items-center">
              <span className="fs-3 me-3">💡</span>
              <div>
                <strong>How to use:</strong> Click the microphone button and speak your command clearly.
                <br />
                <small className="text-muted">
                  Examples: "Go to Dashboard", "Add Vegetable", "Generate Recipe with potato and onion"
                </small>
              </div>
            </div>
          </Alert>

          {/* Voice Control */}
          <div className="text-center mb-4 p-5 bg-light rounded">
            <div className="mb-4">
              <div 
                className={`voice-icon ${isListening ? 'listening' : ''}`}
                style={{
                  fontSize: '5rem',
                  animation: isListening ? 'pulse 1.5s infinite' : 'none'
                }}
              >
                🎤
              </div>
            </div>

            {isListening ? (
              <div>
                <h5 className="text-danger mb-3">Listening...</h5>
                {interimTranscript && (
                  <p className="text-muted mb-3">
                    <em>"{interimTranscript}"</em>
                  </p>
                )}
                <Button variant="danger" size="lg" onClick={stopListening}>
                  ⏹️ Stop Listening
                </Button>
              </div>
            ) : (
              <div>
                <h5 className="text-success mb-3">Ready to Listen</h5>
                <Button variant="success" size="lg" onClick={handleStartListening} className="px-5">
                  🎤 Start Voice Command
                </Button>
              </div>
            )}
          </div>

          {/* Last Command */}
          {lastCommand && (
            <Alert variant="success" className="mb-4">
              <strong>Last Command:</strong> "{lastCommand.transcript}"
              <br />
              <small className="text-muted">
                Action: {lastCommand.result.action} 
                {lastCommand.result.path && ` → ${lastCommand.result.path}`}
              </small>
            </Alert>
          )}

          {/* Quick Actions */}
          <Card className="mb-4">
            <Card.Header className="bg-secondary text-white">
              <h6 className="mb-0">📝 Available Voice Commands</h6>
            </Card.Header>
            <Card.Body>
              <div className="d-flex justify-content-between align-items-center mb-3">
                <p className="mb-0">Need help with voice commands?</p>
                <Button variant="outline-primary" size="sm" onClick={() => setShowHelp(true)}>
                  📖 View All Commands
                </Button>
              </div>

              <Row className="g-2">
                <Col md={6}>
                  <Card className="border-0 bg-light">
                    <Card.Body className="py-2">
                      <small><strong>Navigation:</strong> "Go to Dashboard", "Add Vegetable"</small>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={6}>
                  <Card className="border-0 bg-light">
                    <Card.Body className="py-2">
                      <small><strong>Recipes:</strong> "AI Recipe", "Generate Recipe"</small>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={6}>
                  <Card className="border-0 bg-light">
                    <Card.Body className="py-2">
                      <small><strong>Search:</strong> "Search for potato"</small>
                    </Card.Body>
                  </Card>
                </Col>
                <Col md={6}>
                  <Card className="border-0 bg-light">
                    <Card.Body className="py-2">
                      <small><strong>Special:</strong> "Recipe with potato and onion"</small>
                    </Card.Body>
                  </Card>
                </Col>
              </Row>
            </Card.Body>
          </Card>

          {/* Tips */}
          <Card>
            <Card.Header className="bg-warning text-dark">
              <h6 className="mb-0">💡 Tips for Best Results</h6>
            </Card.Header>
            <Card.Body>
              <ListGroup variant="flush">
                <ListGroup.Item>✓ Speak clearly and at a normal pace</ListGroup.Item>
                <ListGroup.Item>✓ Use the exact command phrases shown</ListGroup.Item>
                <ListGroup.Item>✓ Reduce background noise for better recognition</ListGroup.Item>
                <ListGroup.Item>✓ Wait for the "Ready" status before speaking again</ListGroup.Item>
                <ListGroup.Item>✓ Works best with Chrome, Edge, or Safari browsers</ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Card.Body>
      </Card>

      {/* Help Modal */}
      <Modal show={showHelp} onHide={() => setShowHelp(false)} size="lg">
        <Modal.Header closeButton className="bg-primary text-white">
          <Modal.Title>🎤 All Voice Commands</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <ListGroup>
            {commandsHelp.map((cmd, index) => (
              <ListGroup.Item key={index}>
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <strong className="text-primary">{cmd.command}</strong>
                    <br />
                    <small className="text-muted">{cmd.description}</small>
                  </div>
                </div>
              </ListGroup.Item>
            ))}
          </ListGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowHelp(false)}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>

      {/* CSS for pulse animation */}
      <style jsx>{`
        @keyframes pulse {
          0%, 100% {
            transform: scale(1);
            opacity: 1;
          }
          50% {
            transform: scale(1.1);
            opacity: 0.8;
          }
        }
      `}</style>
    </div>
  );
};

export default VoiceAssistant;