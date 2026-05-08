package com.riwi.intro.repository;

import com.riwi.intro.models.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepositoryImpl {
    private int nextId=1;
    private final List<Event> events = new ArrayList<>();

    public List<Event> findAll(){
        return events;
    }

    public Event findById(int id){
        for (Event event : events){
            if (event.getId() == id){
                return event;
            }
        }
        return null;
    }

    public Event add(Event event){
        event.setId(nextId++);
        events.add(event);
        return event;
    }

    public Event deleteById(int id){
        if (findById(id) == null) return null;
        Event deleted = findById(id);
        int index = events.indexOf(deleted);
        Event remove = events.remove(index);
        return deleted;
    }

    public Event update(Event event){
        Event evento = findById(event.getId());
        if(evento == null) return null;
        evento.setDescription(event.getDescription());
        return evento;
    }
}
