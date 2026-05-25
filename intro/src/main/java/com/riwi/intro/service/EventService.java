package com.riwi.intro.service;

import com.riwi.intro.exception.ResourceNotFoundException;
import com.riwi.intro.models.Event;
import com.riwi.intro.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event save(Event event) {
        return repository.save(event);
    }

    public Event findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + id + " was not found"));
    }

    public Page<Event> findAll(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Event update(Integer id, Event event) {
        Event existingEvent = findById(id);
        existingEvent.setName(event.getName());
        existingEvent.setDate(event.getDate());
        existingEvent.setDescription(event.getDescription());
        return repository.save(existingEvent);
    }

    public void delete(Integer id) {
        Event event = findById(id);
        repository.delete(event);
    }
}
