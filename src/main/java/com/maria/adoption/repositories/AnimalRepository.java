package com.maria.adoption.repositories;

import com.maria.adoption.entities.Animal;
import com.maria.adoption.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    List<Animal> findAllByAdopter(User adopter);
    List<Animal> findAllByPoster(User poster);
}