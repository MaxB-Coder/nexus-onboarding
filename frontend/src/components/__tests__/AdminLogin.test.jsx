import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import AdminLogin from '../AdminLogin';
import * as api from '../../services/api';

vi.mock('../../services/api');

describe('AdminLogin', () => {
  it('expands and submits login form', async () => {
    api.loginAdmin.mockResolvedValueOnce({});

    render(<AdminLogin />);

    fireEvent.click(screen.getByRole('button', { name: /admin login/i }));

    fireEvent.change(screen.getByLabelText(/username/i), {
      target: { value: 'admin' },
    });
    fireEvent.change(screen.getByLabelText(/password/i), {
      target: { value: 'admin123' },
    });

    fireEvent.click(screen.getByRole('button', { name: /sign in/i }));

    await waitFor(() => {
      expect(api.loginAdmin).toHaveBeenCalledWith({
        username: 'admin',
        password: 'admin123',
      });
    });
  });
});
