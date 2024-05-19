package ac.ucr.event.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ac.ucr.event.jpa.entities.PlaceEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {
    Optional<PlaceEntity> findBySite(String site);
}
