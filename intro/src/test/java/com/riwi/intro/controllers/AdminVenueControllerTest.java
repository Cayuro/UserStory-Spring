package com.riwi.intro.controllers;

import com.riwi.intro.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminVenueController.class)
public class AdminVenueControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VenueService service;

    @TestConfiguration
    static class Config {
        @Bean
        VenueService venueService() {
            return mock(VenueService.class);
        }
    }

    @Test
    public void listEmpty_showsMessage() throws Exception {
        when(service.findAll(null, any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venues"))
                .andExpect(model().attributeExists("venues"));
    }
}
