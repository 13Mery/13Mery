package com.maria.adoption.repositories;

import com.maria.adoption.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimalRepository extends JpaRepository<Animal, Integer> {
}
//interfata care