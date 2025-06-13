package com.schedule.eventz.repositories;

import com.schedule.eventz.models.Event;
import com.schedule.eventz.models.Registration;
import com.schedule.eventz.models.Status;
import com.schedule.eventz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    public Registration findByUserAndEvent(User user, Event event);
    public List<Registration> findByUser(User user);
    public List<Registration> findByEvent(Event event);
    public List<Registration> findByEventAndStatus(Event event, Status status);
    public int countByEventAndStatus(Event event, Status status);
}
