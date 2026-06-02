package com.riwi.intro.controllers;

import com.riwi.intro.service.EventService;
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

@WebMvcTest(AdminEventController.class)
public class AdminEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventService service;

    @TestConfiguration
    static class Config {
        @Bean
        EventService eventService() {
            return mock(EventService.class);
        }
    }

    @Test
    public void listEmpty_showsMessage() throws Exception {
        when(service.findAll(null, any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events"))
                .andExpect(model().attributeExists("events"));
    }
}
