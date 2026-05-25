package com.riwi.intro.repository;

import com.riwi.intro.IntroApplication;
import com.riwi.intro.models.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = IntroApplication.class)
class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @Test
    void savesAndFindsVenueById() {
        Venue savedVenue = venueRepository.save(new Venue(null, "Main Hall", "Street 10", 250));

        assertThat(venueRepository.findById(savedVenue.getId()))
                .isPresent()
                .get()
                .extracting(Venue::getName)
                .isEqualTo("Main Hall");
    }

    @Test
    void findsVenuesByNameContainingIgnoringCase() {
        venueRepository.save(new Venue(null, "Central Theater", "Avenue 1", 500));
        venueRepository.save(new Venue(null, "Open Plaza", "Avenue 2", 1000));

        Page<Venue> result = venueRepository.findByNameContainingIgnoreCase(
                "theater",
                PageRequest.of(0, 10, Sort.by("name").ascending())
        );

        assertThat(result.getContent())
                .extracting(Venue::getName)
                .containsExactly("Central Theater");
    }
}
