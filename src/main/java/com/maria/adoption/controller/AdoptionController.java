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

    @GetMapping("/{animal_id}")
    public String animalAdoptPage(Model model, @PathVariable Integer animal_id) {
        AdoptionRequest adoptionRequest = new AdoptionRequest();
        model.addAttribute("adoptionRequest", adoptionRequest);
        model.addAttribute("animalId", animal_id);
        return "adoption/adopt-animal";
    }

    @GetMapping("/requests/{animal_id}")
    public String adoptionRequests(Model model, @PathVariable Integer animal_id, Principal principal) {
        Animal animal = animalRepository.findById(animal_id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        if (principal == null || !animal.getPoster().getUsername().equals(principal.getName())) {
            throw new RuntimeException("Only poster of animal can view adoption requests for it.");
        }
        model.addAttribute("animal", animal);
        model.addAttribute("title", "Adoption Requests for " + animal.getName());
        return "adoption/adoption-requests";
    }

    @PostMapping("/{animal_id}")
    public String animalAdopt(
            @PathVariable Integer animal_id,
            @ModelAttribute AdoptionRequest adoptionRequest,
            Principal principal) {
        Animal animal = animalRepository.findById(animal_id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        adoptionRequest.setAnimal(animal);
        User requester = userRepository.findByUsername(principal.getName());
        adoptionRequest.setRequester(requester);
        adoptionRequestRepository.save(adoptionRequest);

        return "adoption/request-thank-you";
    }

    @GetMapping("/accept/{id}")
    public String acceptAdoption(Model model, @PathVariable Integer id, Principal principal) {
        AdoptionRequest adoptionRequest = adoptionRequestRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        if (principal == null || adoptionRequest.getAnimal().getPoster().getUsername().equals(principal.getName())) {
            adoptionRequest.setAccepted(true);
            model.addAttribute("phone", adoptionRequest.getRequester().getPhoneNumber());
            model.addAttribute("requesterName", adoptionRequest.getRequester().getName());
        }
        else {
            throw new RuntimeException("Adoption offer cannot be accepted by user who didn't post the animal");
        }

        return "adoption/accept-thank-you";
    }
}
