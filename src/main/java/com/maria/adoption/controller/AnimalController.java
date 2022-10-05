package com.maria.adoption.controller;

import com.maria.adoption.entities.Animal;
import com.maria.adoption.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("")
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @GetMapping("/{id}")
    public Animal getAnimalByID(@PathVariable Integer id) {
        return animalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteAnimalById(@PathVariable Integer id) {
        animalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        animalRepository.deleteById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal addAnimal(@RequestBody Animal animal) {
       return this.animalRepository.save(animal);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateAnimal(@PathVariable Integer id, @RequestBody Animal animal) {
        animalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.animalRepository.save(animal);
    }
}
