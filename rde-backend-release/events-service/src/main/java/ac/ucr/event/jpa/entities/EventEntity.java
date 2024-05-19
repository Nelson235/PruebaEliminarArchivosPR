package ac.ucr.event.jpa.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import ac.ucr.event.jpa.entities.PlaceEntity;

@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime datetime;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "availability", nullable = false)
    private int availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "target", nullable = false)
    private Target target;

    @Enumerated(EnumType.STRING)
    @Column(name = "modality", nullable = false)
    private Modality modality;

    @Column(name = "user_id", length = 36)
    private UUID user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceEntity place;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public ac.ucr.event.jpa.entities.EventEntity.Status getStatus() {
        return status;
    }

    public void setStatus(ac.ucr.event.jpa.entities.EventEntity.Status status) {
        this.status = status;
    }

    public ac.ucr.event.jpa.entities.EventEntity.Category getCategory() {
        return category;
    }

    public void setCategory(ac.ucr.event.jpa.entities.EventEntity.Category category) {
        this.category = category;
    }

    public ac.ucr.event.jpa.entities.EventEntity.Target getTarget() {
        return target;
    }

    public void setTarget(ac.ucr.event.jpa.entities.EventEntity.Target target) {
        this.target = target;
    }

    public ac.ucr.event.jpa.entities.EventEntity.Modality getModality() {
        return modality;
    }

    public void setModality(ac.ucr.event.jpa.entities.EventEntity.Modality modality) {
        this.modality = modality;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public PlaceEntity getPlace() {
        return place;
    }

    public void setPlace(PlaceEntity place) {
        this.place = place;
    }

    public enum Status {
        ACTIVE,
        CANCELED,
        FINISHED;

    }

    public enum Category {
        FESTIVAL,
        LIBRARY,
        SPORT,
        CONFERENCE,
        WORKSHOP;

    }

    public enum Target {
        GENERAL,
        ADULT;

    }

    public enum Modality {
        VIRTUAL,
        ONSITE;

    }
}
