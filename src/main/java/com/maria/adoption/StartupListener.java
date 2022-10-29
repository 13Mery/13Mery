package com.maria.adoption;

import com.maria.adoption.configuration.AdoptionConfigurationProperties;
import com.maria.adoption.entities.User;
import com.maria.adoption.services.AnimalService;
import com.maria.adoption.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final AdoptionConfigurationProperties properties;
    private final AnimalService animalService;
    private final UserService userService;

    @Autowired
    public StartupListener(
            AdoptionConfigurationProperties properties,
            AnimalService animalService,
            UserService userService
    ) {
        this.properties = properties;
        this.animalService = animalService;
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.properties.isSeederEnabled()) {
            seedDatabase();
        }
    }

    public void seedDatabase() {
        List<User> users = this.userService.seedTestUsers(4);
        this.animalService.seedTestAnimals(7, users);
    }
}
