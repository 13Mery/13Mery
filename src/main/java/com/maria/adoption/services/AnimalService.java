package com.maria.adoption.services;

import com.maria.adoption.entities.Animal;
import com.maria.adoption.entities.User;
import com.maria.adoption.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final Random random = new Random();

    @Autowired
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public void seedTestAnimals(int n, List<User> users) {
        if (n > 7) {
            throw new RuntimeException(
                    "Test animal seeder does not support more than 7 animals."
            );
        }

        String[] names = {
                "Aiurica",
                "Rex",
                "Nero",
                "Scooby",
                "Alma",
                "Hector",
                "Toto"
        };

        int[] ages = {2, 4, 1, 5, 12, 1, 13};

        String[] descriptions = {
                "S-a pierdut, saracul",
                "Cel mai cuminte baiat",
                "Bun pentru copii",
                "Are nevoie de o sansa",
                "Este foarte bland",
                "Gasit in sant",
                "Este schiop, micul de el"
        };

        for (int i=0; i<n; i++) {
            User poster = users.get(random.nextInt(users.size()));
            Animal animal = new Animal();
            animal.setName(names[i]);
            animal.setAge(ages[i]);
            animal.setDescription(descriptions[i]);
            animal.setPoster(poster);
            animalRepository.save(animal);
        }
    }
}
