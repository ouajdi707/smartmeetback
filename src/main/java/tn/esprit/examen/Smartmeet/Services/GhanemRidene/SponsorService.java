package tn.esprit.examen.Smartmeet.Services.GhanemRidene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.examen.Smartmeet.entities.GhanemRidene.Sponsor;
import tn.esprit.examen.Smartmeet.entities.Users.TypeUserRole;
import tn.esprit.examen.Smartmeet.entities.Users.Users;
import tn.esprit.examen.Smartmeet.repositories.GhanemRiden.SponsorRepository;
import tn.esprit.examen.Smartmeet.repositories.Users.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SponsorService implements ISponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        if (sponsor.getUser() != null && sponsor.getUser().getUserID() != null) {
            Users user = userRepository.findById(sponsor.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + sponsor.getUser().getUserID()));
            
            // Verify that the user has the SPONSOR role
            if (!user.getUserRole().contains(TypeUserRole.SPONSOR)) {
                throw new RuntimeException("User must have SPONSOR role to be associated with a sponsor");
            }
            
            // Check if user is already associated with a sponsor
            if (sponsorRepository.findByUser(user).isPresent()) {
                throw new RuntimeException("User is already associated with a sponsor");
            }
            
            sponsor.setUser(user);
        }
        return sponsorRepository.save(sponsor);
    }

    @Override
    public Sponsor updateSponsor(Sponsor sponsor) {
        if (!sponsorRepository.existsById(sponsor.getIdSponsor())) {
            throw new RuntimeException("Sponsor not found with id: " + sponsor.getIdSponsor());
        }

        if (sponsor.getUser() != null && sponsor.getUser().getUserID() != null) {
            Users user = userRepository.findById(sponsor.getUser().getUserID())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + sponsor.getUser().getUserID()));
            
            // Verify that the user has the SPONSOR role
            if (!user.getUserRole().contains(TypeUserRole.SPONSOR)) {
                throw new RuntimeException("User must have SPONSOR role to be associated with a sponsor");
            }
            
            // Check if user is already associated with a different sponsor
            sponsorRepository.findByUser(user).ifPresent(existingSponsor -> {
                if (!existingSponsor.getIdSponsor().equals(sponsor.getIdSponsor())) {
                    throw new RuntimeException("User is already associated with another sponsor");
                }
            });
            
            sponsor.setUser(user);
        }
        return sponsorRepository.save(sponsor);
    }

    @Override
    public void deleteSponsor(Long id) {
        sponsorRepository.deleteById(id);
    }

    @Override
    public Sponsor getSponsorById(Long id) {
        return sponsorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    public List<Users> getAvailableSponsorUsers() {
        return userRepository.findAll().stream()
            .filter(user -> user.getUserRole().contains(TypeUserRole.SPONSOR))
            .filter(user -> !sponsorRepository.findByUser(user).isPresent())
            .collect(Collectors.toList());
    }
} 