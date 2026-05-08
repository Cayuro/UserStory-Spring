package com.riwi.intro.controllers;

import com.riwi.intro.models.Event;
import com.riwi.intro.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    public final EventService service;

    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @GetMapping
    public List<Event> getEvents() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable int id){
        return service.findById(id);
    }

    @PostMapping
    public Event create(@RequestBody Event event){
        return service.save(event);
    }


}
