import { useState, useEffect } from 'react';
import './EventsList.css';
import { Link } from 'react-router-dom';

function EventsList() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/events');
        const data = await response.json();
        setEvents(data);
      } catch (error) {
        console.error('Error fetching events:', error);
      } finally {
        setLoading(false);
      }
    };
  
    fetchEvents();
  }, []);
  return (
    <div className="events-container">
      <h2 className="events-header">Upcoming Events</h2>
      {loading ? (
        <div className="loading">Loading events...</div>
      ) : (
        <div>
          {events.length === 0 ? (
            <div className="no-events">No events found.</div>
          ) : (
            <div className="events-grid">
              {events.map(event => (
                <Link to={`/event/${event.id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                    <div key={event.id} className="event-card">
                    <h3 className="event-title">{event.name}</h3>
                    <p className="event-description">{event.description}</p>
                    <div className="event-details">
                      <div className="event-detail">
                        <strong>Date:</strong> {new Date(event.startTime).toLocaleDateString()}
                      </div>
                      <div className="event-detail">
                        <strong>Location:</strong> {event.location}
                      </div>
                      <div className="event-detail">
                        <strong>Organizer:</strong> {event.organizer.firstName} {event.organizer.lastName}
                      </div>
                      <div className="event-detail">
                        <strong>Capacity:</strong> {event.maxAttendees} people
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default EventsList;