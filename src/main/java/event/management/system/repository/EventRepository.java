package event.management.system.repository;

import org.springframework.stereotype.Repository;

import event.management.system.entity.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	List<Event> findByOrganizerUsername(String username);

}
