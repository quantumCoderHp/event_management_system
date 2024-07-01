package event.management.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import event.management.system.entity.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
	
	List<Registration> findByEventId(Long eventId);
    List<Registration> findByAttendeeUsername(String username);
    List<Registration> findByOrganizerUsername(String organizerUsername);

}
