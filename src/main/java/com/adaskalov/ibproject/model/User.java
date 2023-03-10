package com.adaskalov.ibproject.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

@Data
@Entity(name = "is_user")
public class User {

    @Id
    private Long embg;

    @Enumerated(value = EnumType.STRING)
    private UserType type;

    private String name;
    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    private List<Prescription> prescriptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return embg.equals(user.embg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(embg);
    }

    @Override
    public String toString() {
        return "User{" +
                "embg=" + embg +
                ", name='" + name + '\'' +
                '}';
    }
}
