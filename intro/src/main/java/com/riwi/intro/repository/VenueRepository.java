package com.riwi.intro.repository;

import com.riwi.intro.models.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Integer> {

    Page<Venue> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
