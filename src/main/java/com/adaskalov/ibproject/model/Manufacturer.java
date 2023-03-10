package com.adaskalov.ibproject.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Manufacturer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;

    private String name;


    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.REMOVE)
    private List<Medicine> medicineList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
