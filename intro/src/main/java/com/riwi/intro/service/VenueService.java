package com.riwi.intro.service;

import com.riwi.intro.models.Venue;
import com.riwi.intro.repository.VenueRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
    private final VenueRepositoryImpl repository;

    public VenueService(VenueRepositoryImpl repository) {
        this.repository = repository;
    }

    public Venue save(Venue venue) {
        return repository.add(venue);
    }

    public Venue findById(int id){
        return repository.findById(id);
    }

    public List<Venue> findAll(){
        return repository.findAll();
    }
    public Venue update(Venue venue) {
        return repository.update(venue);
    }
    public Venue delete(int id){
        return repository.deleteById(id);
    }
}
