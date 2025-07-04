import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import EventsList from './EventsList';
import EventDetails from './EventDetails';
import Login from './Login';
import UserDashboard from './UserDashboard';
import CreateEvent from './CreateEvent';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  const canCreateEvent = user && (user.role === 'ADMIN' || user.role === 'EVENT_ORGANIZER');

  return (
    <Router>
      <div className="App">
        <header style={{ 
          display: 'flex', 
          justifyContent: 'space-between', 
          alignItems: 'center',
          padding: '20px', 
          background: '#f8f9fa', 
          marginBottom: '20px' 
        }}>
          <div>
            <Link to="/" style={{ textDecoration: 'none', color: 'inherit' }}>
              <h1 style={{ color: '#2c3e50', margin: 0 }}>EventZ</h1>
              <p style={{ color: '#666', margin: '5px 0 0 0' }}>Event Management System</p>
            </Link>
          </div>
          
          <nav>
            {user ? (
              <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
                {canCreateEvent && (
                  <Link 
                    to="/create-event" 
                    style={{ 
                      background: '#28a745',
                      color: 'white', 
                      padding: '8px 16px', 
                      textDecoration: 'none', 
                      borderRadius: '4px',
                      fontSize: '0.9rem'
                    }}
                  >
                    Create Event
                  </Link>
                )}
                <Link 
                  to="/dashboard" 
                  style={{ 
                    color: '#007bff', 
                    textDecoration: 'none',
                    fontWeight: '500'
                  }}
                >
                  My Dashboard
                </Link>
                <span>Welcome, {user.firstName}!</span>
                <button 
                  onClick={handleLogout}
                  style={{ 
                    background: '#dc3545', 
                    color: 'white', 
                    border: 'none', 
                    padding: '8px 16px', 
                    borderRadius: '4px',
                    cursor: 'pointer'
                  }}
                >
                  Logout
                </button>
              </div>
            ) : (
              <Link 
                to="/login" 
                style={{ 
                  background: '#007bff', 
                  color: 'white', 
                  padding: '8px 16px', 
                  textDecoration: 'none', 
                  borderRadius: '4px' 
                }}
              >
                Login
              </Link>
            )}
          </nav>
        </header>
        
        <Routes>
          <Route path="/" element={<EventsList />} />
          <Route path="/event/:id" element={<EventDetails />} />
          <Route path="/login" element={<Login setUser={setUser} />} />
          <Route path="/dashboard" element={<UserDashboard />} />
          <Route path="/create-event" element={<CreateEvent />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;