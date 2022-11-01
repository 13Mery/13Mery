package com.maria.adoption.controller;

import com.maria.adoption.entities.Animal;
import com.maria.adoption.entities.User;
import com.maria.adoption.repositories.AnimalRepository;
import com.maria.adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnimalController(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String animalsPage(Model model) {
        List<Animal> animals = this.animalRepository.findAllByAdopter(null);
        model.addAttribute("formAnimal", new Animal());
        model.addAttribute("animals", animals);
        model.addAttribute("title", "All Animals for Adoption");
        return "animal-list";
    }

    @GetMapping("my-offers")
    public String myAdoptionOffers(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Animal> animals = this.animalRepository.findAllByPoster(user);
        model.addAttribute("formAnimal", new Animal());
        model.addAttribute("animals", animals);
        model.addAttribute("title", "My Adoption Offers");
        return "animal-list";
    }

    @GetMapping("/{id}")
    public String animalDetails(Model model, @PathVariable Integer id, Principal principal) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean ownAnimal = principal != null && animal.getPoster().getUsername().equals(principal.getName());
        model.addAttribute("animal", animal);
        model.addAttribute("ownAnimal", ownAnimal);
        return "animal";
    }

    @GetMapping("/add")
    public String animalForm(Model model) {
        model.addAttribute("formAnimal", new Animal());
        return "add-animal";
    }

    @PostMapping
    public String animalSubmit(@ModelAttribute Animal animal, Model model, Principal principal) {
        User poster = userRepository.findByUsername(principal.getName());
        animal.setPoster(poster);
        this.animalRepository.save(animal);
        return animalsPage(model);
    }

    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable Integer id) {
        animalRepository.deleteById(id);
    }
}
