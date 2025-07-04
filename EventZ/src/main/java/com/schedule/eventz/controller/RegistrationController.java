package com.schedule.eventz.controller;

import com.schedule.eventz.models.Registration;
import com.schedule.eventz.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Registration registerForEvent(@RequestParam Long userId, @RequestParam Long eventId) {
        return registrationService.registerUserForEvent(userId, eventId);
    }

    @DeleteMapping("/{registrationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRegistration(@PathVariable Long registrationId) {
        registrationService.cancelRegistration(registrationId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Registration> getUserRegistrations(@PathVariable Long userId) {
        return registrationService.getUserRegistrations(userId);
    }

    @GetMapping("/event/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Registration> getEventRegistrations(@PathVariable Long eventId) {
        return registrationService.getEventRegistrations(eventId);
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isUserRegistered(@RequestParam Long userId, @RequestParam Long eventId) {
        return registrationService.isUserRegistered(userId, eventId).isPresent();
    }
}
