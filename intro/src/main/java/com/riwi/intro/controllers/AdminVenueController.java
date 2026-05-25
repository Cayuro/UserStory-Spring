package com.riwi.intro.controllers;

import com.riwi.intro.models.Venue;
import com.riwi.intro.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/venues")
public class AdminVenueController {
    private final VenueService service;

    public AdminVenueController(VenueService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        var page = service.findAll(null, org.springframework.data.domain.Pageable.unpaged());
        model.addAttribute("venues", page.getContent());
        model.addAttribute("venue", new Venue());
        return "admin/venues";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("venue", new Venue());
        return "admin/venue_form";
    }

    @PostMapping
    public String save(@ModelAttribute Venue venue) {
        service.save(venue);
        return "redirect:/admin/venues";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Venue venue = service.findById(id);
        model.addAttribute("venue", venue);
        return "admin/venue_form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/venues";
    }
}
