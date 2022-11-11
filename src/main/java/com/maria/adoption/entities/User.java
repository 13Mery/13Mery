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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "poster")
    private Set<Animal> adoptionOffers;

    @OneToMany(mappedBy = "adopter")
    private Set<Animal> adoptedAnimals;

    @OneToMany(mappedBy = "requester")
    private Set<AdoptionRequest> adoptionRequests;
}
