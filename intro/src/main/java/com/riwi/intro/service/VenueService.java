package com.riwi.intro.service;

import com.riwi.intro.exception.ResourceNotFoundException;
import com.riwi.intro.models.Venue;
import com.riwi.intro.repository.VenueRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private final VenueRepository repository;

    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    public Venue save(Venue venue) {
        return repository.save(venue);
    }

    public Venue findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue with id " + id + " was not found"));
    }

    public Slice<Venue> findAll(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Venue update(Integer id, Venue venue) {
        Venue existingVenue = findById(id);
        existingVenue.setName(venue.getName());
        existingVenue.setAddress(venue.getAddress());
        existingVenue.setCapacity(venue.getCapacity());
        return repository.save(existingVenue);
    }

    public void delete(Integer id) {
        Venue venue = findById(id);
        repository.delete(venue);
    }
}
