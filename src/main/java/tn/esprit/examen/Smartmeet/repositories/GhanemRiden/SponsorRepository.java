package tn.esprit.examen.Smartmeet.repositories.GhanemRiden;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.examen.Smartmeet.entities.GhanemRidene.Sponsor;
import tn.esprit.examen.Smartmeet.entities.Users.Users;

import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findByUser(Users user);
} 