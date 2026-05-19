package com.riwi.intro.repository;

import com.riwi.intro.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Integer> {

    List<Venue> findByNameContainingIgnoreCase(String name);
}
