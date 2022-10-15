package com.maria.adoption.services;

import com.maria.adoption.entities.Role;
import com.maria.adoption.entities.User;
import com.maria.adoption.repositories.RoleRepository;
import com.maria.adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        Role adminRole = roleRepository.findByRole("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setRole("ADMIN");
            roleRepository.save(adminRole);
        }
        User admin = this.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            System.out.println("What?!");
            admin.setUsername("admin");
            admin.setName("Maria");
            admin.setPassword("admin");
            admin.setRoles(Set.of(adminRole));
            this.saveUser(admin);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }


}
