package com.schedule.eventz.service;

import org.springframework.stereotype.Service;
import com.schedule.eventz.models.Event;
import com.schedule.eventz.repositories.EventRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventSearchService {
    private final EventRepository eventRepository;

    public EventSearchService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return eventRepository.findAll();
        }
        return eventRepository.findByKeyword(keyword.trim());
    }

    public List<Event> findEventsWithAvailableSpots() {
        return eventRepository.findEventsWithAvailableSpots();
    }

    public List<Event> searchByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return eventRepository.findAll();
        }
        return eventRepository.findByLocationContainingIgnoreCase(location.trim());
    }

    public List<Event> findEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    public List<Event> findEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findByDateRange(startDate, endDate);
    }

    public List<Event> findEventsUserNotRegisteredFor(Long userId) {
        return eventRepository.findEventsUserNotRegisteredFor(userId);
    }

    public List<Event> searchWithMultipleCriteria(String keyword, String location, Long organizerId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Event> events = eventRepository.findAll();

        return events.stream().filter(event -> {
            boolean matchesKeyword = keyword == null || keyword.trim().isEmpty() ||
                (event.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                 event.getDescription().toLowerCase().contains(keyword.toLowerCase()));
            boolean matchesLocation = location == null || location.trim().isEmpty() ||
                event.getLocation().toLowerCase().contains(location.toLowerCase());
            boolean matchesOrganizer = organizerId == null || event.getOrganizer().getId().equals(organizerId);
            boolean matchesDateRange = (startDate == null || !event.getStartTime().isBefore(startDate)) &&
                                       (endDate == null || !event.getStartTime().isAfter(endDate));
            return matchesKeyword && matchesLocation && matchesOrganizer && matchesDateRange;
        }).toList();
    }
}
