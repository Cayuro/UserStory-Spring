package com.riwi.intro.controllers;

import com.riwi.intro.dto.EventSummaryDTO;
import com.riwi.intro.models.Event;
import com.riwi.intro.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    @Operation(
            summary = "List events with optimized projection",
            description = "Returns only active events. Soft-deleted rows are hidden by Hibernate SQL restrictions."
    )
    public Slice<EventSummaryDTO> getEvents(
            @Parameter(description = "Optional name filter, case insensitive and partial")
            @RequestParam(required = false) String name,
            @Parameter(description = "Optional city filter, case insensitive and partial")
            @RequestParam(required = false) String city,
            @Parameter(description = "Optional category filter, case insensitive and partial")
            @RequestParam(required = false) String category,
            @Parameter(description = "Optional minimum capacity filter")
            @RequestParam(required = false) Integer capacity,
            @Parameter(description = "Optional start date in ISO format")
            @RequestParam(required = false) String dateFrom,
            @Parameter(description = "Optional end date in ISO format")
            @RequestParam(required = false) String dateTo,
            @ParameterObject Pageable pageable) {
        return service.findAll(name, city, category, capacity, dateFrom, dateTo, pageable);
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
