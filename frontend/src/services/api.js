// src/services/api.js
import axios from 'axios';

const API_BASE = `${import.meta.env.VITE_API_BASE}/api/applications`;
const ADMIN_BASE = `${import.meta.env.VITE_API_BASE}/api/admin`;

export const fetchApplications = () => axios.get(API_BASE + '/');

export const submitApplication = (data) => axios.post(API_BASE + '/', data);

export const updateStatus = (id, status) =>
  axios.patch(`${API_BASE}/${id}/status`, { status });

export const loginAdmin = (credentials) =>
  axios.post(`${ADMIN_BASE}/login`, credentials);
