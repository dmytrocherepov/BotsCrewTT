package org.example.botscrewtesttask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "lectors")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    @Enumerated(EnumType.STRING)
    Degree degree;
    @ManyToMany
    @JoinTable(
            name = "lecturer_department",
            joinColumns = @JoinColumn(name = "lecturer_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    Set<Department> departments;
    Double salary;
}
