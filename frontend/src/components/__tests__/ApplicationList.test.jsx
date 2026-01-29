import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import ApplicationList from '../ApplicationList';
import * as api from '../../services/api';

vi.mock('../../services/api');

describe('ApplicationList', () => {
  it('renders application data', async () => {
    api.fetchApplications.mockResolvedValueOnce({
      data: [
        {
          id: 1,
          companyName: 'Acme Corp',
          companyNumber: '12345678',
          contactEmail: 'test@acme.com',
          annualTurnover: 1000,
          riskRating: 'LOW',
          status: 'PENDING',
        },
      ],
    });

    render(<ApplicationList />);

    expect(screen.getByText(/loading/i)).toBeInTheDocument();

    expect(await screen.findByText('Acme Corp')).toBeInTheDocument();
    expect(screen.getByText(/pending/i)).toBeInTheDocument();
  });

  it('calls updateStatus when approve is clicked', async () => {
    api.fetchApplications.mockResolvedValue({
      data: [
        {
          id: 1,
          companyName: 'Acme Corp',
          companyNumber: '12345678',
          contactEmail: 'test@acme.com',
          annualTurnover: 1000,
          riskRating: 'LOW',
          status: 'PENDING',
        },
      ],
    });
    api.updateStatus.mockResolvedValueOnce({});

    render(<ApplicationList />);

    await screen.findByText('Acme Corp');

    fireEvent.click(screen.getByRole('button', { name: /approve/i }));

    await waitFor(() => {
      expect(api.updateStatus).toHaveBeenCalledWith(1, 'APPROVED');
    });
  });
});
