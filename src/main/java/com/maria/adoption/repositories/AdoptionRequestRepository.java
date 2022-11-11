package com.maria.adoption.repositories;

import com.maria.adoption.entities.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {

}