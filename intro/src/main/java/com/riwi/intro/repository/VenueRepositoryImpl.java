package com.riwi.intro.repository;

import com.riwi.intro.models.Venue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VenueRepositoryImpl {
    private final List<Venue> venues = new ArrayList<>();

    public List<Venue> findAll(){
        return venues;
    }

    public Venue findById(int id){
        for (Venue venue : venues){
            if (venue.getId() == id){
                return venue;
            }
        }
        return null;
    }

    public Venue add(Venue venue){
        venues.add(venue);
        return venue;
    }

    public Venue deleteById(int id){
        if (findById(id) == null) return null;
        Venue deleted = findById(id);
        int index = venues.indexOf(deleted);
        Venue remove = venues.remove(index);
        return deleted;
    }

    public Venue update(Venue venue){
        Venue updated= findById(venue.getId());
        venue.setName(updated.getName());
        return updated;
    }
}
