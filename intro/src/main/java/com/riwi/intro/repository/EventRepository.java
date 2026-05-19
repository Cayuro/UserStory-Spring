package com.riwi.intro.repository;

import com.riwi.intro.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
