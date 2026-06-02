package com.riwi.intro.service;

import com.riwi.intro.dto.EventForm;
import com.riwi.intro.dto.EventSummaryDTO;
import com.riwi.intro.exception.ResourceNotFoundException;
import com.riwi.intro.models.Category;
import com.riwi.intro.models.Event;
import com.riwi.intro.models.Venue;
import com.riwi.intro.repository.EventRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    private final EventRepository repository;
    private final VenueService venueService;
    private final CategoryService categoryService;

    public EventService(EventRepository repository, VenueService venueService, CategoryService categoryService) {
        this.repository = repository;
        this.venueService = venueService;
        this.categoryService = categoryService;
    }

    public Event save(Event event) {
        return repository.save(resolveAssociations(event));
    }

    @Transactional(readOnly = true)
    public Event findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + id + " was not found"));
    }

    @Transactional(readOnly = true)
    public Slice<EventSummaryDTO> findAll(String name, String city, String category, Integer capacity, String dateFrom, String dateTo, Pageable pageable) {
        return repository.searchSummaries(name, city, category, capacity, dateFrom, dateTo, pageable);
    }

    public Slice<EventSummaryDTO> findAllByName(String name, Pageable pageable) {
        return findAll(name, null, null, null, null, null, pageable);
    }

    public Event update(Integer id, Event event) {
        Event existingEvent = findById(id);
        existingEvent.setName(event.getName());
        existingEvent.setDate(event.getDate());
        existingEvent.setDescription(event.getDescription());
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            existingEvent.setVenue(resolveVenue(event.getVenue()));
        }
        if (event.getCategories() != null && !event.getCategories().isEmpty()) {
            existingEvent.setCategories(resolveCategories(event.getCategories()));
        }
        return repository.save(existingEvent);
    }

    public void delete(Integer id) {
        Event event = findById(id);
        repository.delete(event);
    }

    @Transactional(readOnly = true)
    public EventForm findFormById(Integer id) {
        Event event = findById(id);
        EventForm form = new EventForm();
        form.setId(event.getId());
        form.setName(event.getName());
        form.setDate(event.getDate());
        form.setDescription(event.getDescription());
        form.setVenueId(event.getVenue().getId());
        form.setCategoryIds(event.getCategories().stream().map(Category::getId).toList());
        return form;
    }

    public Event saveForm(EventForm form) {
        Event event = form.getId() == null ? new Event() : findById(form.getId());
        event.setName(form.getName());
        event.setDate(form.getDate());
        event.setDescription(form.getDescription());
        event.setVenue(resolveVenue(form.getVenueId()));
        event.setCategories(resolveCategories(form.getCategoryIds()));
        return repository.save(event);
    }

    private Event resolveAssociations(Event event) {
        event.setVenue(resolveVenue(event.getVenue()));
        event.setCategories(resolveCategories(event.getCategories()));
        return event;
    }

    private Venue resolveVenue(Venue venue) {
        if (venue == null || venue.getId() == null) {
            throw new IllegalArgumentException("Event venue is required");
        }
        return venueService.findById(venue.getId());
    }

    private Venue resolveVenue(Integer venueId) {
        if (venueId == null) {
            throw new IllegalArgumentException("Event venue is required");
        }
        return venueService.findById(venueId);
    }

    private Set<Category> resolveCategories(Set<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return new LinkedHashSet<>();
        }

        Set<Integer> ids = categories.stream()
                .map(Category::getId)
                .filter(id -> id != null)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (ids.isEmpty()) {
            return new LinkedHashSet<>();
        }

        List<Category> managedCategories = categoryService.findAllById(ids);
        return new LinkedHashSet<>(managedCategories);
    }

    private Set<Category> resolveCategories(List<Integer> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(categoryService.findAllById(new LinkedHashSet<>(categoryIds)));
    }
}
