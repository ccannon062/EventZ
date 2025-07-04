import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './UserDashboard.css';

function UserDashboard() {
  const [registrations, setRegistrations] = useState([]);
  const [loading, setLoading] = useState(true);
  const user = JSON.parse(localStorage.getItem('user') || 'null');
  const token = localStorage.getItem('token');

  useEffect(() => {
    if (!user || !token) return;

    const fetchUserRegistrations = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/registrations/user/${user.id}`,
          {
            headers: {
              'Authorization': `Bearer ${token}`
            }
          }
        );
        const data = await response.json();
        setRegistrations(data);
      } catch (error) {
        console.error('Error fetching registrations:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserRegistrations();
  }, [user, token]);

  const handleCancelRegistration = async (registrationId) => {
    if (!window.confirm('Are you sure you want to cancel this registration?')) {
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/registrations/${registrationId}`,
        {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      );

      if (response.ok) {
        setRegistrations(registrations.filter(reg => reg.id !== registrationId));
        alert('Registration cancelled successfully');
      } else {
        alert('Failed to cancel registration');
      }
    } catch (error) {
      alert('Failed to cancel registration');
    }
  };

  if (!user) {
    return (
      <div className="dashboard-container">
        <h2>Please log in to view your dashboard</h2>
        <Link to="/login">Go to Login</Link>
      </div>
    );
  }

  if (loading) return <div className="loading">Loading your registrations...</div>;

  return (
    <div className="dashboard-container">
      <h2>Welcome, {user.firstName}!</h2>
      <h3>Your Event Registrations</h3>
      
      {registrations.length === 0 ? (
        <div className="no-registrations">
          <p>You haven't registered for any events yet.</p>
          <Link to="/" className="browse-events-btn">Browse Events</Link>
        </div>
      ) : (
        <div className="registrations-grid">
          {registrations.map(registration => (
            <div key={registration.id} className="registration-card">
              <div className="registration-header">
                <h4>{registration.event.name}</h4>
                <span className={`status-badge ${registration.status.toLowerCase()}`}>
                  {registration.status}
                </span>
              </div>
              
              <p className="event-description">{registration.event.description}</p>
              
              <div className="event-details">
                <div className="detail-item">
                  <strong>Date:</strong> {new Date(registration.event.startTime).toLocaleDateString()}
                </div>
                <div className="detail-item">
                  <strong>Time:</strong> {new Date(registration.event.startTime).toLocaleTimeString()}
                </div>
                <div className="detail-item">
                  <strong>Location:</strong> {registration.event.location}
                </div>
                <div className="detail-item">
                  <strong>Registered:</strong> {new Date(registration.registrationDate).toLocaleDateString()}
                </div>
              </div>
              
              <div className="registration-actions">
                <Link 
                  to={`/event/${registration.event.id}`} 
                  className="view-event-btn"
                >
                  View Event
                </Link>
                <button 
                  onClick={() => handleCancelRegistration(registration.id)}
                  className="cancel-btn"
                >
                  Cancel Registration
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default UserDashboard;