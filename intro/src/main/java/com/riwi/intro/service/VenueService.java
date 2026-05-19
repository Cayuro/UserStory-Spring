package com.riwi.intro.service;

import com.riwi.intro.exception.ResourceNotFoundException;
import com.riwi.intro.models.Venue;
import com.riwi.intro.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Venue> findAll() {
        return repository.findAll();
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
