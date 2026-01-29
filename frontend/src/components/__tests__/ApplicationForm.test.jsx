import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ApplicationForm from '../ApplicationForm';
import * as api from '../../services/api';

vi.mock('../../services/api');

describe('ApplicationForm', () => {
  it('submits form and shows success', async () => {
    api.submitApplication.mockResolvedValueOnce({});

    render(<ApplicationForm />);

    fireEvent.change(screen.getByLabelText(/company name/i), {
      target: { value: 'Acme Corp' },
    });
    fireEvent.change(screen.getByLabelText(/company number/i), {
      target: { value: '12345678' },
    });
    fireEvent.change(screen.getByLabelText(/annual turnover/i), {
      target: { value: '1000' },
    });

    fireEvent.click(screen.getByRole('button', { name: /submit/i }));

    await waitFor(() => {
      expect(api.submitApplication).toHaveBeenCalledWith({
        companyName: 'Acme Corp',
        companyNumber: '12345678',
        annualTurnover: 1000,
      });
    });

    expect(
      await screen.findByText(/application submitted/i),
    ).toBeInTheDocument();
  });
});
