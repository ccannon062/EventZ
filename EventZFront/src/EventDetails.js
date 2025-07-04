import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import './EventDetails.css';

function EventDetails() {
  const [event, setEvent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [registering, setRegistering] = useState(false);
  const [isRegistered, setIsRegistered] = useState(false);
  const { id } = useParams();
  
  const user = JSON.parse(localStorage.getItem('user') || 'null');
  const token = localStorage.getItem('token');

  useEffect(() => {
    const fetchEvent = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/events/${id}`);
        const data = await response.json();
        setEvent(data);
      } catch (error) {
        console.error('Error fetching event:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchEvent();
  }, [id]);

  const handleRegistration = async () => {
    if (!user || !token) {
      alert('Please log in to register for events');
      return;
    }

    setRegistering(true);
    try {
      const response = await fetch(
        `http://localhost:8080/api/registrations?userId=${user.id}&eventId=${id}`,
        {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        }
      );

      if (response.ok) {
        setIsRegistered(true);
        alert('Successfully registered for the event!');
      } else {
        alert('Registration failed. Please try again.');
      }
    } catch (error) {
      alert('Registration failed. Please try again.');
    } finally {
      setRegistering(false);
    }
  };

  if (loading) return <div className="loading">Loading event details...</div>;
  if (!event) return <div className="error">Event not found</div>;

  return (
    <div className="event-details-container">
      <Link to="/" className="back-link">← Back to Events</Link>
      
      <div className="event-details-card">
        <h1 className="event-title">{event.name}</h1>
        <p className="event-description">{event.description}</p>
        
        <div className="event-info-grid">
          <div className="info-item">
            <strong>Start Time:</strong>
            <span>{new Date(event.startTime).toLocaleString()}</span>
          </div>
          <div className="info-item">
            <strong>End Time:</strong>
            <span>{new Date(event.endTime).toLocaleString()}</span>
          </div>
          <div className="info-item">
            <strong>Location:</strong>
            <span>{event.location}</span>
          </div>
          <div className="info-item">
            <strong>Capacity:</strong>
            <span>{event.maxAttendees} people</span>
          </div>
          <div className="info-item">
            <strong>Organizer:</strong>
            <span>{event.organizer.firstName} {event.organizer.lastName}</span>
          </div>
          <div className="info-item">
            <strong>Contact:</strong>
            <span>{event.organizer.email}</span>
          </div>
        </div>
        
        {user ? (
          <button 
            className={isRegistered ? "register-btn registered" : "register-btn"}
            onClick={handleRegistration}
            disabled={isRegistered || registering}
          >
            {registering ? 'Registering...' : 
             isRegistered ? 'Successfully Registered!' : 'Register for Event'}
          </button>
        ) : (
          <Link to="/login" className="register-btn" style={{ textDecoration: 'none', textAlign: 'center', display: 'block' }}>
            Login to Register
          </Link>
        )}
      </div>
    </div>
  );
}

export default EventDetails;