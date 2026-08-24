import { render, screen } from '@testing-library/react';
import App from './App';

test('renders the login screen for unauthenticated users', () => {
  render(<App />);
  expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument();
  expect(screen.getByLabelText(/username or email/i)).toBeInTheDocument();
});
