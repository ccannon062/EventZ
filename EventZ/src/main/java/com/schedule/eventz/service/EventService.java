package com.schedule.eventz.service;

import com.schedule.eventz.models.Event;
import org.springframework.stereotype.Service;
import com.schedule.eventz.repositories.EventRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    //CREATE OPERATIONS
    public List<Event> saveAllEvents(List<Event> events) {
        LocalDateTime now = LocalDateTime.now();
        for (Event event : events) {
            event.setCreatedAt(now);
        }
        return eventRepository.saveAll(events);
    }

    public void saveEvent(Event event) {
        event.setCreatedAt(java.time.LocalDateTime.now());
        eventRepository.save(event);
    }

    //READ OPERATIONS
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public List<Event> getEventsByLocation(String location) {
        return eventRepository.findByLocation(location);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStartTimeAfter(java.time.LocalDateTime.now());
    }

    public List<Event> getEventsBetweenDates(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return eventRepository.findByStartTimeBetween(start, end);
    }

    //UPDATE OPERATIONS
    public Event updateEvent(Long id, Event event) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        return eventRepository.save(event);
    }

    //DELETE OPERATIONS
    public void deleteEventById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}
