package com.schedule.eventz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schedule.eventz.models.Event;
import com.schedule.eventz.models.Role;
import com.schedule.eventz.models.User;
import com.schedule.eventz.repositories.EventRepository;
import com.schedule.eventz.repositories.UserRepository;
import com.schedule.eventz.service.EventService;
import com.schedule.eventz.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final EventService eventService;
    private final ResourceLoader resourceLoader;
    private final UserRepository userRepository;
    private final UserService userService;

    public DataInitializer(EventRepository eventRepository, EventService eventService, ResourceLoader resourceLoader,
                           UserRepository userRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.resourceLoader = resourceLoader;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
            User admin = new User(
                    "Alice",
                    "Anderson",
                    "alice@eventz.com",
                    "password123",
                    "555-0101",
                    Role.ADMIN
            );
            userService.createUser(admin);
            User organizer1 = new User(
                    "Bob",
                    "Johnson",
                    "bob@eventz.com",
                    "password123",
                    "555-0102",
                    Role.EVENT_ORGANIZER
            );
            userService.createUser(organizer1);
            User organizer2 = new User(
                    "Carol",
                    "Smith",
                    "carol@eventz.com",
                    "password123",
                    "555-0103",
                    Role.EVENT_ORGANIZER
            );
            userService.createUser(organizer2);
        }
        if (eventRepository.count() == 0) {
            Resource resource = resourceLoader.getResource("classpath:dummy-events.json");
            try (InputStream inputStream = resource.getInputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                List<Event> events = objectMapper.readValue(inputStream, new TypeReference<List<Event>>() {});
                for (Event event : events) {
                    event.setOrganizer(userService.getAllUsers().get((int) (Math.random() * userService.getAllUsers().size())));
                    eventService.saveEvent(event);
                }
            }
        }
    }
}
