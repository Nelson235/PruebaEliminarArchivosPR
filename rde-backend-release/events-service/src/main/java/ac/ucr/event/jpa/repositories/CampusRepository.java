package ac.ucr.event.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ac.ucr.event.jpa.entities.CampusEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CampusRepository extends JpaRepository<CampusEntity, UUID> {
    Optional<CampusEntity> findByName(String name);
}
