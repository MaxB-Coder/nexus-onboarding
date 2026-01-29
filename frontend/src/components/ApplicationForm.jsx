import React, { useState } from 'react';
import { submitApplication } from '../services/api';
import {
  Box,
  Button,
  Paper,
  Stack,
  TextField,
  Typography,
  Alert,
} from '@mui/material';

const initialState = {
  companyName: '',
  companyNumber: '',
  annualTurnover: '',
};

export default function ApplicationForm({ onSuccess }) {
  const [form, setForm] = useState(initialState);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccess(false);
    setLoading(true);
    try {
      await submitApplication({
        ...form,
        annualTurnover: parseFloat(form.annualTurnover),
      });
      setForm(initialState);
      setSuccess(true);
      if (onSuccess) onSuccess();
    } catch (err) {
      setError(
        err.response?.data?.message ||
          'Submission failed. Please check your input.',
      );
    }
    setLoading(false);
  };

  return (
    <Paper
      sx={{
        p: 3,
        mb: 4,
        borderRadius: 3,
        border: '1px solid rgba(228, 233, 240, 1)',
        boxShadow: '0 12px 30px rgba(13, 35, 60, 0.08)',
      }}
    >
      <Typography variant='h6' gutterBottom sx={{ color: '#102a43' }}>
        Submit New Application
      </Typography>
      <Box component='form' onSubmit={handleSubmit}>
        <Stack spacing={2}>
          <TextField
            label='Company Name'
            name='companyName'
            value={form.companyName}
            onChange={handleChange}
            required
            fullWidth
          />
          <TextField
            label='Company Number'
            name='companyNumber'
            value={form.companyNumber}
            onChange={handleChange}
            required
            fullWidth
            inputProps={{ pattern: '\\d{8}', maxLength: 8 }}
            helperText='Exactly 8 digits'
          />
          <TextField
            label='Annual Turnover'
            name='annualTurnover'
            value={form.annualTurnover}
            onChange={handleChange}
            required
            fullWidth
            type='number'
            inputProps={{ min: 0, step: '0.01' }}
          />
          <Button
            type='submit'
            variant='contained'
            disabled={loading}
            sx={{
              backgroundColor: '#00aef0',
              '&:hover': { backgroundColor: '#0095c9' },
              textTransform: 'uppercase',
              fontWeight: 600,
              letterSpacing: 0.6,
            }}
          >
            {loading ? 'Submitting...' : 'Submit'}
          </Button>
          {error && <Alert severity='error'>{error}</Alert>}
          {success && <Alert severity='success'>Application submitted!</Alert>}
        </Stack>
      </Box>
    </Paper>
  );
}
