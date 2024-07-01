package event.management.system.controllers;

import java.util.List;

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
import event.management.system.entity.Registration;
import event.management.system.entity.User;
import event.management.system.repository.EventRepository;
import event.management.system.repository.RegistrationRepository;
import event.management.system.repository.UserRepository;
@RestController
@RequestMapping("/registrations")
public class RegistrationController {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	public ResponseEntity<?> registerForEvent(@Valid @RequestBody Registration registration,
			Authentication authentication) {
		// Get authenticated user's username
		String username = ((UserDetails) authentication.getPrincipal()).getUsername();

		// Check if the user is an attendee
		User user = userRepository.findByUsername(username);
		if (user == null || !user.getRole().equals("ROLE_ATTENDEE")) {
			return ResponseEntity.status(403).body("Access denied");
		}

		// Set the attendee's username
		registration.setAttendeeUsername(username);

		// Check if the event exists
		Event event = eventRepository.findById(registration.getEventId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid event ID"));

		// Save the registration
		Registration savedRegistration = registrationRepository.save(registration);

		return ResponseEntity.ok(savedRegistration);
	}

	@GetMapping
	public ResponseEntity<?> getAllRegistrations(Authentication authentication) {
		// Get authenticated user's username
		String username = ((UserDetails) authentication.getPrincipal()).getUsername();

		// Check if the user is an admin or organizer
		User user = userRepository.findByUsername(username);
		if (user == null || (!user.getRole().equals("ROLE_ADMIN") && !user.getRole().equals("ROLE_ORGANIZER"))) {
			return ResponseEntity.status(403).body("Access denied");
		}

		// Fetch registrations
		List<Registration> registrations;
		if (user.getRole().equals("ROLE_ADMIN")) {
			registrations = registrationRepository.findAll();
		} else {
			registrations = registrationRepository.findByOrganizerUsername(username);
		}

		return ResponseEntity.ok(registrations);
	}

}
