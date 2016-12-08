package anax.pang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "pang_event")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Event {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer eventId;
	
	@Column(name = "event_name", nullable = false)
	private String name;
	
	public Event() {
		super();
	}

	public Event(Integer eventId) {
		super();
		this.eventId = eventId;
	}

	public Event(Integer eventId, String name) {
		super();
		this.eventId = eventId;
		this.name = name;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
