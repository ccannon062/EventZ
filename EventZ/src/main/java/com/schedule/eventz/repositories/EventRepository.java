package com.schedule.eventz.repositories;

import com.schedule.eventz.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByLocation(String location);
    List<Event> findByStartTimeAfter(LocalDateTime dateTime);
    List<Event> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT e FROM Event e WHERE e.name LIKE %:keyword% OR e.description LIKE %:keyword%")
    List<Event> findByKeyword(@Param("keyword") String keyword);
    @Query("SELECT e FROM Event e WHERE SIZE(e.registrations) < e.maxAttendees")
    List<Event> findEventsWithAvailableSpots();
    List<Event> findByLocationContainingIgnoreCase(String location);
    @Query("SELECT e FROM Event e WHERE e.organizer.id = :organizerId")
    List<Event> findByOrganizerId(@Param("organizerId") Long organizerId);
    @Query("SELECT e FROM Event e WHERE e.startTime >= :startDate AND e.startTime <= :endDate")
    List<Event> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("SELECT e FROM Event e WHERE e.id NOT IN (SELECT r.event.id FROM Registration r WHERE r.user.id = :userId)")
    List<Event> findEventsUserNotRegisteredFor(@Param("userId") Long userId);
}
