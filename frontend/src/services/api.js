// src/services/api.js
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/applications';
const ADMIN_BASE = 'http://localhost:8080/api/admin';

export const fetchApplications = () => axios.get(API_BASE + '/');

export const submitApplication = (data) => axios.post(API_BASE + '/', data);

export const updateStatus = (id, status) =>
  axios.patch(`${API_BASE}/${id}/status`, { status });

export const loginAdmin = (credentials) =>
  axios.post(`${ADMIN_BASE}/login`, credentials);
