package tn.esprit.examen.Smartmeet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.Smartmeet.Services.GhanemRidene.ISponsorService;
import tn.esprit.examen.Smartmeet.entities.GhanemRidene.Sponsor;

import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private ISponsorService sponsorService;

    @PostMapping
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.addSponsor(sponsor);
    }

    @PutMapping
    public Sponsor updateSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.updateSponsor(sponsor);
    }

    @DeleteMapping("/{id}")
    public void deleteSponsor(@PathVariable Long id) {
        sponsorService.deleteSponsor(id);
    }

    @GetMapping("/{id}")
    public Sponsor getSponsorById(@PathVariable Long id) {
        return sponsorService.getSponsorById(id);
    }

    @GetMapping
    public List<Sponsor> getAllSponsors() {
        return sponsorService.getAllSponsors();
    }
} 