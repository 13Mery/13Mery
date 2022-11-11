package com.maria.adoption.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

    @OneToMany(mappedBy = "animal")
    private Set<AdoptionRequest> adoptionRequests;

    public String getPosterName() {
        return this.poster.getName();
    }

    public long openRequestCount() {
        return adoptionRequests
                .stream()
                .filter(adoptionRequest -> !adoptionRequest.getAccepted())
                .count();
    }
}
