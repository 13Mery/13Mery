package com.maria.adoption.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer age;
    private String description;

    // The user who proposed the animal for adoption
    @ManyToOne
    @JoinColumn(name="poster_id", nullable = false)
    private User poster;

    @ManyToOne
    @JoinColumn(name="adopter_id")
    private User adopter;

    public String getPosterName() {
        return this.poster.getName();
    }
}
