import React, { useEffect, useState } from 'react';
import { fetchApplications, updateStatus } from '../services/api';
import './ApplicationList.css';

const ApplicationList = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      const response = await fetchApplications();
      setApplications(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to load applications');
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (id, status) => {
    try {
      await updateStatus(id, status);
      loadApplications();
    } catch (err) {
      setError('Failed to update status');
    }
  };

  const getStatusBadgeClass = (status) => {
    const statusClasses = {
      PENDING: 'badge-warning',
      APPROVED: 'badge-success',
      REJECTED: 'badge-danger',
    };
    return `badge ${statusClasses[status] || 'badge-secondary'}`;
  };

  const getRiskBadgeClass = (risk) => {
    return `badge ${risk === 'HIGH' ? 'badge-danger' : 'badge-success'}`;
  };

  const formatCurrency = (value) => {
    if (value >= 1000000) {
      return `$${(value / 1000000).toFixed(1)}M`;
    } else if (value >= 1000) {
      return `$${(value / 1000).toFixed(0)}K`;
    }
    return `$${value?.toLocaleString()}`;
  };

  if (loading) return <div className='loading-spinner'>Loading...</div>;
  if (error) return <div className='error-message'>{error}</div>;

  return (
    <div className='application-list'>
      <h2>Client Applications</h2>
      {applications.length === 0 ? (
        <p className='empty-state'>No applications found.</p>
      ) : (
        <div className='table-container'>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Company Name</th>
                <th>Contact Email</th>
                <th>Annual Turnover</th>
                <th>Risk Rating</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {applications.map((app) => (
                <tr key={app.id}>
                  <td>{app.id}</td>
                  <td>{app.companyName}</td>
                  <td>{app.contactEmail}</td>
                  <td>{formatCurrency(app.annualTurnover)}</td>
                  <td>
                    <span className={getRiskBadgeClass(app.riskRating)}>
                      {app.riskRating}
                    </span>
                  </td>
                  <td>
                    <span className={getStatusBadgeClass(app.status)}>
                      {app.status}
                    </span>
                  </td>
                  <td className='actions'>
                    <button
                      className='btn btn-approve'
                      onClick={() => handleStatusChange(app.id, 'APPROVED')}
                      disabled={app.status === 'APPROVED'}
                    >
                      Approve
                    </button>
                    <button
                      className='btn btn-reject'
                      onClick={() => handleStatusChange(app.id, 'REJECTED')}
                      disabled={app.status === 'REJECTED'}
                    >
                      Reject
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default ApplicationList;
