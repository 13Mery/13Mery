package com.maria.adoption.services;

import com.maria.adoption.entities.Role;
import com.maria.adoption.entities.User;
import com.maria.adoption.repositories.RoleRepository;
import com.maria.adoption.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

        Role userRole = roleRepository.findByRole("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setRole("USER");
            roleRepository.save(userRole);
        }
    }

    private void seedAdminUser() {
        Role adminRole = roleRepository.findByRole("ADMIN");
        User admin = this.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setName("Maria");
            admin.setPassword("admin");
            admin.setRoles(Set.of(adminRole));
            this.saveUser(admin);
        }
    }

    public List<User> seedTestUsers(int n) {
        Role userRole = roleRepository.findByRole("USER");
        List<User> testUsers = new ArrayList<>();
        for (int i=0; i<n; i++) {
            User user = new User();
            int num = i + 1;
            user.setUsername("test" + num);
            user.setPassword("test");
            user.setName("Test User " + num);
            user.setRoles(Set.of(userRole));
            testUsers.add(user);
            this.saveUser(user);
        }
        return testUsers;
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
