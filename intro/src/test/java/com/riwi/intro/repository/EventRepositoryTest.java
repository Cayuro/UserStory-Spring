package com.riwi.intro.repository;

import com.riwi.intro.IntroApplication;
import com.riwi.intro.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = IntroApplication.class)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void savesAndFindsEventById() {
        Event savedEvent = eventRepository.save(new Event(
                null,
                "Spring Conf",
                "2026-06-10",
                "Conference for Spring developers"
        ));

        assertThat(eventRepository.findById(savedEvent.getId()))
                .isPresent()
                .get()
                .extracting(Event::getName)
                .isEqualTo("Spring Conf");
    }

    @Test
    void findsEventsByNameContainingIgnoringCase() {
        eventRepository.save(new Event(null, "Java Summit", "2026-07-01", "Backend event"));
        eventRepository.save(new Event(null, "Frontend Meetup", "2026-07-02", "Frontend event"));

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
            eventRepository.save(new Event(
                    null,
                    String.format("Event %02d", i),
                    "2026-08-01",
                    "Generated event for pagination test"
            ));
        }

        Page<Event> page = eventRepository.findAll(PageRequest.of(0, 5, Sort.by("name").ascending()));

        assertThat(page.getContent()).hasSize(5);
        assertThat(page.getTotalElements()).isEqualTo(50);
        assertThat(page.getTotalPages()).isEqualTo(10);
        assertThat(page.getContent()).extracting(Event::getName)
                .containsExactly("Event 01", "Event 02", "Event 03", "Event 04", "Event 05");
    }
}
