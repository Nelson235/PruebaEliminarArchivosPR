package dev.events.authentication.jpa.entities;

// AdminEntity.java
import jakarta.persistence.*;
import java.util.Date;
import org.hibernate.annotations.JdbcTypeCode;
import java.util.Objects;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "admins")
public class AdminEntity {

    @Id
    @Column(name = "id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @Column(name = "email_address")
    private String email;
    private String password;

    private String name;
    private String phone;

    private Date birth;

    public AdminEntity() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() { return phone;}
    public void setPhone(String phone) { this.phone = phone;}

    public Date getBirth() { return birth;}
    public void setBirth(Date birth) { this.birth = birth;}

    public String getName() { return name;}
    public void setName(String name) { this.name = name;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(birth, that.birth) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birth, email, phone, password);
    }

}
