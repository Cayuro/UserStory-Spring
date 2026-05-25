package com.riwi.intro.controllers;

import com.riwi.intro.models.Event;
import com.riwi.intro.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    public final EventService service;

    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @GetMapping
    @Operation(summary = "List events with pagination and sorting")
    public Page<Event> getEvents(
            @Parameter(description = "Optional name filter using a derived query")
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable) {
        return service.findAll(name, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an event by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public Event getEvent(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an event")
    public Event create(@RequestBody Event event) {
        return service.save(event);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public Event update(@PathVariable Integer id, @RequestBody Event event) {
        return service.update(id, event);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an event")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
