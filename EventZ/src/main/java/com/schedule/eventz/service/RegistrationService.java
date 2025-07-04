package com.schedule.eventz.service;

import com.schedule.eventz.models.Event;
import com.schedule.eventz.models.Registration;
import com.schedule.eventz.models.Status;
import com.schedule.eventz.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final UserService userService;
    private final EventService eventService;
    private final EmailService emailService;

    public RegistrationService(RegistrationRepository registrationRepository, UserService userService, EventService eventService, EmailService EmailService) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.eventService = eventService;
        this.emailService = EmailService;
    }

    public Registration registerUserForEvent(Long Id, Long eventId) {
        var user = userService.getUserById(Id);
        var event = eventService.getEventById(eventId);
        if (registrationRepository.findByUserAndEvent(user, event) != null) {
            throw new RuntimeException("User is already registered for this event");
        } else if (registrationRepository.countByEventAndStatus(event, Status.CONFIRMED) >= event.getMaxAttendees()) {
            var registration = new Registration(user, event, Status.WAITLISTED);
            emailService.sendRegistrationConfirmation(user, event, "WAITLISTED");
            return registrationRepository.save(registration);
        } else {
            var registration = new Registration(user, event, Status.CONFIRMED);
            emailService.sendRegistrationConfirmation(user, event, "Confirmed");
            return registrationRepository.save(registration);
        }
    }

    public void cancelRegistration(Long registrationId) {
        var registration = registrationRepository.findById(registrationId).orElseThrow(
                () -> new RuntimeException("Registration not found"));
        registrationRepository.delete(registration);
        if(registration.getStatus() == Status.CONFIRMED) {
            promoteFromWaitlist(registration.getEvent().getId());
        }
    }

    public Optional<Registration> isUserRegistered(Long userId, Long eventId) {
        var user = userService.getUserById(userId);
        var event = eventService.getEventById(eventId);
        return Optional.ofNullable(registrationRepository.findByUserAndEvent(user, event));
    }

    public int getConfirmedCount(Event event) {
        return registrationRepository.countByEventAndStatus(event, Status.CONFIRMED);
    }

    public void promoteFromWaitlist(Long eventId) {
        var event = eventService.getEventById(eventId);
        var registrations = registrationRepository.findByEventAndStatus(event, Status.WAITLISTED);
        if (registrations.isEmpty()) {
            return;
        }
        for (Registration registration : registrations) {
            registration.setStatus(Status.CONFIRMED);
            emailService.sendWaitlistPromotion(registration.getUser(), event);
            registrationRepository.save(registration);
            break;
        }
    }

    public List<Registration> getUserRegistrations(Long userId) {
        var user = userService.getUserById(userId);
        return registrationRepository.findByUser(user);
    }

    public List<Registration> getEventRegistrations(Long eventId) {
        var event = eventService.getEventById(eventId);
        return registrationRepository.findByEvent(event);
    }
}

