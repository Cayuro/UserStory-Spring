package com.riwi.intro.controllers;

import com.riwi.intro.dto.EventForm;
import com.riwi.intro.service.CategoryService;
import com.riwi.intro.service.EventService;
import com.riwi.intro.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService service;
    private final VenueService venueService;
    private final CategoryService categoryService;

    public AdminEventController(EventService service, VenueService venueService, CategoryService categoryService) {
        this.service = service;
        this.venueService = venueService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @org.springdoc.core.annotations.ParameterObject org.springframework.data.domain.Pageable pageable,
            Model model) {
        var page = service.findAll(name, city, category, capacity, dateFrom, dateTo, pageable);
        model.addAttribute("events", page);
        model.addAttribute("name", name);
        model.addAttribute("city", city);
        model.addAttribute("category", category);
        model.addAttribute("capacity", capacity);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        return "admin/events";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("event", new EventForm());
        model.addAttribute("venues", venueService.findAll(null, org.springframework.data.domain.Pageable.unpaged()).getContent());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/event_form";
    }

    @PostMapping
    public String save(@ModelAttribute("event") EventForm eventForm) {
        service.saveForm(eventForm);
        return "redirect:/admin/events";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("event", service.findFormById(id));
        model.addAttribute("venues", venueService.findAll(null, org.springframework.data.domain.Pageable.unpaged()).getContent());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/event_form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/events";
    }
}
