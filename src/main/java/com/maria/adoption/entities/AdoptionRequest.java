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
@Table(name = "adoption_requests")
public class AdoptionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="request_id")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name="requester_id", nullable = false)
    private User requester;

    private String ownOtherAnimals;
    private String haveChildren;
    private String homeDescription;

    private Boolean accepted = false;
}