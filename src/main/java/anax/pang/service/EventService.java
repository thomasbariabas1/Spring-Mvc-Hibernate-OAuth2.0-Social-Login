package anax.pang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import anax.pang.model.Event;
import anax.pang.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	public Event getEventById(Integer eventId) {
		return eventRepository.getEvent(eventId);
	}
	
	public Event getEventByName(String name) {
		return eventRepository.getEventByName(name);
	}
}
