package com.schedule.eventz.controller;

import com.schedule.eventz.models.Event;
import com.schedule.eventz.service.EventSearchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events/search")
public class EventSearchController {

    private final EventSearchService eventSearchService;

    public EventSearchController(EventSearchService eventSearchService) {
        this.eventSearchService = eventSearchService;
    }

    @GetMapping("/keyword")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchByKeyword(@RequestParam String keyword) {
        return eventSearchService.searchByKeyword(keyword);
    }

    @GetMapping("/location")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchByLocation(@RequestParam String location) {
        return eventSearchService.searchByLocation(location);
    }

    @GetMapping("/available-spots")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchEventsWithAvailableSpots() {
        return eventSearchService.findEventsWithAvailableSpots();
    }

    @GetMapping("/organizer/{organizerId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchByOrganizer(@PathVariable Long organizerId) {
        return eventSearchService.findEventsByOrganizer(organizerId);
    }

    @GetMapping("/date-range")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return eventSearchService.findEventsByDateRange(start, end);
    }

    @GetMapping("/not-registered/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchEventsUserNotRegisteredFor(@PathVariable Long userId) {
        return eventSearchService.findEventsUserNotRegisteredFor(userId);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> searchMultipleCriteria(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long organizerId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return eventSearchService.searchWithMultipleCriteria(
                keyword,
                location,
                organizerId,
                startDate != null ? LocalDateTime.parse(startDate) : null,
                endDate != null ? LocalDateTime.parse(endDate) : null
        );
    }
}
