package com.maria.adoption.controller;

import com.maria.adoption.entities.AdoptionRequest;
import com.maria.adoption.entities.Animal;
import com.maria.adoption.entities.User;
import com.maria.adoption.repositories.AdoptionRequestRepository;
import com.maria.adoption.repositories.AnimalRepository;
import com.maria.adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/adopt")
public class AdoptionController {
    private final AdoptionRequestRepository adoptionRequestRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdoptionController(
            AnimalRepository animalRepository,
            UserRepository userRepository,
            AdoptionRequestRepository adoptionRequestRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    @GetMapping("/{id}")
    public String animalAdoptPage(Model model, @PathVariable Integer id) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        model.addAttribute("adoptionRequest", adoptionRequest);
        model.addAttribute("animalId", id);
        return "adoption/adopt-animal";
    }

    @PostMapping("/{id}")
    public String animalAdopt(
            @PathVariable Integer id,
            @ModelAttribute AdoptionRequest adoptionRequest,
            Principal principal) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        adoptionRequest.setAnimal(animal);
        User requester = userRepository.findByUsername(principal.getName());
        adoptionRequest.setRequester(requester);
        adoptionRequestRepository.save(adoptionRequest);

        return "adoption/request-thank-you";
    }
}
