package com.riwi.intro.repository;

import com.riwi.intro.IntroApplication;
import com.riwi.intro.models.Category;
import com.riwi.intro.models.Event;
import com.riwi.intro.models.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Set;

@DataJpaTest(properties = "spring.flyway.enabled=false")
@ContextConfiguration(classes = IntroApplication.class)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Venue cachedVenue;
    private Category cachedCategory;

    private Event createEvent(String name, String date, String description) {
        if (cachedVenue == null) {
            cachedVenue = venueRepository.save(new Venue(null, "Main Hall", "Street 10", "Bogotá", 250));
        }
        if (cachedCategory == null) {
            cachedCategory = categoryRepository.save(new Category(null, "Tech", "Tech event"));
        }

        Event event = new Event(null, name, date, description);
        event.setVenue(cachedVenue);
        event.setCategories(Set.of(cachedCategory));
        return eventRepository.save(event);
    }

    @Test
    void savesAndFindsEventById() {
        Event savedEvent = createEvent(
                "Spring Conf",
                "2026-06-10",
                "Conference for Spring developers"
        );

        assertThat(eventRepository.findById(savedEvent.getId()))
                .isPresent()
                .get()
                .extracting(Event::getName)
                .isEqualTo("Spring Conf");
    }

    @Test
    void findsEventsByNameContainingIgnoringCase() {
        createEvent("Java Summit", "2026-07-01", "Backend event");
        createEvent("Frontend Meetup", "2026-07-02", "Frontend event");

        Page<Event> result = eventRepository.findByNameContainingIgnoreCase(
                "java",
                PageRequest.of(0, 10, Sort.by("name").ascending())
        );

        assertThat(result.getContent())
                .extracting(Event::getName)
                .containsExactly("Java Summit");
    }

    @Test
    void returnsRequestedPageWithPaginationMetadata() {
        for (int i = 1; i <= 50; i++) {
            createEvent(
                    String.format("Event %02d", i),
                    "2026-08-01",
                    "Generated event for pagination test"
            );
        }

        Page<Event> page = eventRepository.findAll(PageRequest.of(0, 5, Sort.by("name").ascending()));

        assertThat(page.getContent()).hasSize(5);
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent()).extracting(Event::getName)
                .containsExactly("Event 01", "Event 02", "Event 03", "Event 04", "Event 05");
    }
}
