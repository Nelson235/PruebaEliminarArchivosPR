package ac.ucr.event.jpa.entities;

// HeadquarterEntity.java
import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.Objects;
import org.hibernate.type.SqlTypes;

import java.util.UUID;
@Entity
@Table(name = "headquarters")
public class HeadquarterEntity {

    @Id
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

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


}