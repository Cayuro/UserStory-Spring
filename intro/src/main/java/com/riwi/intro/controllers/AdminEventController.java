package com.riwi.intro.controllers;

import com.riwi.intro.models.Event;
import com.riwi.intro.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService service;

    public AdminEventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        var page = service.findAll(null, org.springframework.data.domain.Pageable.unpaged());
        model.addAttribute("events", page.getContent());
        model.addAttribute("event", new Event());
        return "admin/events";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/event_form";
    }

    @PostMapping
    public String save(@ModelAttribute Event event) {
        service.save(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Event event = service.findById(id);
        model.addAttribute("event", event);
        return "admin/event_form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/admin/events";
    }
}
