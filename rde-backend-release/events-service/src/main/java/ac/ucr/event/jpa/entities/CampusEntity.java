package ac.ucr.event.jpa.entities;

// HeadquarterEntity.java
import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.Objects;
import org.hibernate.type.SqlTypes;
import ac.ucr.event.jpa.entities.*;
import java.util.UUID;


@Entity
@Table(name = "campus")
public class CampusEntity {

    @Id
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarters_id", nullable = false)
    private HeadquarterEntity headquarters;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HeadquarterEntity getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(HeadquarterEntity headquarters) {
        this.headquarters = headquarters;
    }


}