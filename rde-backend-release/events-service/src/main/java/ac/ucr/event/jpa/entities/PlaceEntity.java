package ac.ucr.event.jpa.entities;

// HeadquarterEntity.java
import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.Objects;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import ac.ucr.event.jpa.entities.*;
@Entity
@Table(name = "places")
public class PlaceEntity {

    @Id
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "site", nullable = false, length = 255)
    private String site;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    private CampusEntity campus;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public CampusEntity getCampus() {
        return campus;
    }

    public void setCampus(CampusEntity campus) {
        this.campus = campus;
    }


}