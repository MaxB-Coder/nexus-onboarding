import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '../App';

vi.mock('../components/ApplicationForm', () => ({
  default: () => <div>Mock Application Form</div>,
}));

vi.mock('../components/ApplicationList', () => ({
  default: () => <div>Mock Application List</div>,
}));

vi.mock('../components/AdminLogin', () => ({
  default: () => <div>Mock Admin Login</div>,
}));

describe('App', () => {
  it('renders the logo text and header content', () => {
    render(<App />);

    expect(screen.getByText(/nexus onboarding/i)).toBeInTheDocument();
    expect(screen.getByText(/client onboarding portal/i)).toBeInTheDocument();
  });
});
