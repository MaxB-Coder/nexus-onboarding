import React, { useState } from 'react';
import { loginAdmin } from '../services/api';
import {
  Box,
  Button,
  Paper,
  Stack,
  TextField,
  Typography,
  Alert,
  Collapse,
} from '@mui/material';

const initialState = {
  username: '',
  password: '',
};

export default function AdminLogin({ onSuccess }) {
  const [form, setForm] = useState(initialState);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [expanded, setExpanded] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await loginAdmin(form);
      setForm(initialState);
      if (onSuccess) onSuccess();
    } catch (err) {
      setError(
        err.response?.data?.message || 'Login failed. Please try again.',
      );
    }
    setLoading(false);
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !loading) {
      e.preventDefault();
      handleSubmit(e);
    }
  };

  return (
    <Paper
      sx={{
        p: 2,
        mt: 4,
        borderRadius: 3,
        border: '1px solid rgba(228, 233, 240, 1)',
        boxShadow: '0 12px 30px rgba(13, 35, 60, 0.08)',
        maxWidth: 460,
        mx: 'auto',
      }}
    >
      <Button
        type='button'
        onClick={() => setExpanded((prev) => !prev)}
        sx={{
          width: '100%',
          justifyContent: 'space-between',
          textTransform: 'none',
          fontWeight: 600,
          color: '#102a43',
          px: 1,
        }}
      >
        <span>Admin Login</span>
        <span>{expanded ? '−' : '+'}</span>
      </Button>
      <Collapse in={expanded} timeout='auto'>
        <Box component='form' onSubmit={handleSubmit} sx={{ mt: 2 }}>
          <Stack spacing={2} sx={{ maxWidth: 420, mx: 'auto' }}>
            <TextField
              label='Username'
              name='username'
              value={form.username}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
              required
              fullWidth
            />
            <TextField
              label='Password'
              name='password'
              type='password'
              value={form.password}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
              required
              fullWidth
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
              {loading ? 'Signing in...' : 'Sign in'}
            </Button>
            {error && <Alert severity='error'>{error}</Alert>}
          </Stack>
        </Box>
      </Collapse>
    </Paper>
  );
}
