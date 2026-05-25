package com.riwi.intro.controllers;

import com.riwi.intro.models.Venue;
import com.riwi.intro.service.VenueService;
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
@RequestMapping("/venues")
public class VenueController {
    private final VenueService service;

    public VenueController(VenueService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "List venues with pagination and sorting")
    public Page<Venue> getVenues(
            @Parameter(description = "Optional name filter using a derived query")
            @RequestParam(required = false) String name,
            @ParameterObject Pageable pageable) {
        return service.findAll(name, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a venue by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue found"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public Venue getVenue(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a venue")
    public Venue create(@RequestBody Venue venue) {
        return service.save(venue);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing venue")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venue updated"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public Venue update(@PathVariable Integer id, @RequestBody Venue venue) {
        return service.update(id, venue);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a venue")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted"),
            @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
