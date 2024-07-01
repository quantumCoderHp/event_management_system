package event.management.system.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import event.management.system.entity.Event;
import event.management.system.entity.User;
import event.management.system.repository.EventRepository;
import event.management.system.repository.UserRepository;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> createEvent(@Valid @RequestBody Event event, Authentication authentication) {
		// Get authenticated user's username
		String username = ((UserDetails) authentication.getPrincipal()).getUsername();

		// Check if the user is an organizer or admin
		User user = userRepository.findByUsername(username);
		if (user == null || (!user.getRole().equals("ROLE_ORGANIZER") && !user.getRole().equals("ROLE_ADMIN"))) {
			return ResponseEntity.status(403).body("Access denied");
		}

		// Set the organizer's username
		event.setOrganizerUsername(username);

		// Save the event
		Event savedEvent = eventRepository.save(event);

		return ResponseEntity.ok(savedEvent);
	}

	@GetMapping
	public ResponseEntity<?> getAllEvents() {
		return ResponseEntity.ok(eventRepository.findAll());
	}

}
