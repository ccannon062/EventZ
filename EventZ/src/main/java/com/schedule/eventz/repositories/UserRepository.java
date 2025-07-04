package com.schedule.eventz.repositories;

import com.schedule.eventz.models.Role;
import com.schedule.eventz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsActive(boolean isActive);
    boolean existsByEmail(String email);
}
