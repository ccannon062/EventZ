package com.schedule.eventz.controller;

import com.schedule.eventz.models.Event;
import com.schedule.eventz.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getEvents(@RequestParam(required = false) String location) {
        if (location != null) {
            return eventService.getEventsByLocation(location);
        }
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventById(@PathVariable Long id) {
        eventService.deleteEventById(id);
    }

    @GetMapping("/upcoming")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/date-range")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getEventsByDateRange(@RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return eventService.getEventsBetweenDates(start, end);
    }
}
