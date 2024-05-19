package ac.ucr.event.jpa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ac.ucr.event.jpa.entities.HeadquarterEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HeadquarterRepository extends JpaRepository<HeadquarterEntity, UUID> {
    Optional<HeadquarterEntity> findByName(String name);
}
