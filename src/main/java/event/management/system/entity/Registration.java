package event.management.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long eventId; // link to Event
	private String attendeeUsername; // link to User

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getAttendeeUsername() {
		return attendeeUsername;
	}

	public void setAttendeeUsername(String attendeeUsername) {
		this.attendeeUsername = attendeeUsername;
	}

}
