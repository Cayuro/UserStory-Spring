package com.riwi.intro.service;

import com.riwi.intro.models.Event;
import com.riwi.intro.repository.EventRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepositoryImpl repository;

    public EventService(EventRepositoryImpl repository) {
        this.repository = repository;
    }

    public Event save(Event event) {
        return repository.add(event);
    }

    public Event findById(int id){
        return repository.findById(id);
    }

    public List<Event> findAll(){
        return repository.findAll();
    }
    public Event update(Event event) {
        return repository.update(event);
    }
    public Event delete(int id){
        return repository.deleteById(id);
    }
}
