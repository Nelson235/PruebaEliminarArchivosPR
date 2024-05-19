package ac.ucr.event.jpa.entities;

// HeadquarterEntity.java
import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.Objects;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.UUID;
import ac.ucr.event.jpa.entities.EventEntity;
@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @Column(name = "id", length = 36)
    private UUID id;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "status", nullable = false, length = 255)
    private String status = "active";

    @Column(name = "user_id", length = 36)
    private UUID user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event_id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getUser() {
        return user_id;
    }

    public void setUser(UUID user_id) {
        this.user_id = user_id;
    }


}