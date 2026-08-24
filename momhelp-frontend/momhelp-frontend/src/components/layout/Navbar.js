import { Navbar, Nav, Container, Dropdown } from 'react-bootstrap';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { FaUser, FaSignOutAlt } from 'react-icons/fa';
//import { processVoiceCommand } from '../../utils/voiceCommands';
import authService from '../../services/authService';
import './NavigationBar.css';
const NavigationBar = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const user = authService.getCurrentUser();

  const isActive = (path) => {
    return location.pathname === path ? 'active' : '';
  };

  // const handleVoiceCommand = (transcript) => {
  //   const result = processVoiceCommand(transcript);
  //   if (result.action === 'navigate') {
  //     navigate(result.path);
  //   }
  // };

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  return (
    <Navbar bg="success" variant="dark" expand="lg" className="shadow">
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold">
          🥬 May I Help You...Mom!
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto align-items-center">
            <Nav.Link as={Link} to="/" className={isActive('/')}>
              Dashboard
            </Nav.Link>
            <Nav.Link as={Link} to="/vegetables" className={isActive('/vegetables')}>
              Manage Vegetables
            </Nav.Link>
            <Nav.Link as={Link} to="/lets-use" className={isActive('/lets-use')}>
              Lets Use
            </Nav.Link>

            <Nav.Link as={Link} to="/spoiled" className={isActive('/spoiled')}>
              Spoiled Veg
            </Nav.Link>

            <Nav.Link as={Link} to="/auto-suggest" className={isActive('/auto-suggest')}>
               Auto Suggest
            </Nav.Link>

            <Nav.Link as={Link} to="/shopping" className={isActive('/shopping')}>
             Shopping List
            </Nav.Link>


            {/* <Nav.Link as={Link} to="/voice-assistant" className={isActive('/voice-assistant')}>
              🎤 Voice
            </Nav.Link> */}
           
            {/* <div className="ms-2">
              <VoiceButton 
                onTranscript={handleVoiceCommand}
                variant="light"
                size="sm"
              />
            </div> */}

            {/* User Profile Dropdown */}
            {user && (
              <Dropdown align="end" className="ms-3">
                <Dropdown.Toggle variant="light" size="sm">
                  <FaUser className="me-2" />
                  {user.username}
                </Dropdown.Toggle>

                <Dropdown.Menu>
                  <Dropdown.Header>
                    {user.fullName}
                    <br />
                    <small className="text-muted">{user.email}</small>
                  </Dropdown.Header>
                  <Dropdown.Divider />
                  <Dropdown.Item onClick={handleLogout}>
                    <FaSignOutAlt className="me-2" />
                    Logout
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavigationBar;
