import React, { useState } from 'react';
import { Container, CssBaseline, Box, Typography } from '@mui/material';
import ApplicationForm from './components/ApplicationForm';
import ApplicationList from './components/ApplicationList';
import AdminLogin from './components/AdminLogin';
import './App.css';

function App() {
  const [refresh, setRefresh] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  const handleSuccess = () => setRefresh((r) => !r);

  return (
    <div className='App'>
      <CssBaseline />
      <header className='app-header'>
        <div className='app-header-content'>
          <div className='logo-text'>Nexus Onboarding</div>
        </div>
      </header>
      <main className='app-main'>
        <Container maxWidth='md' className='content-container'>
          <Box className='page-header'>
            <Typography variant='h5' component='h1'>
              Client Onboarding Portal
            </Typography>
            <Typography variant='body2' className='page-subtitle'>
              Submit new applications and review risk status in real time.
            </Typography>
          </Box>
          <ApplicationForm onSuccess={handleSuccess} />
          {!isAdmin && <AdminLogin onSuccess={() => setIsAdmin(true)} />}
          {isAdmin && <ApplicationList key={refresh} />}
        </Container>
      </main>
    </div>
  );
}

export default App;
