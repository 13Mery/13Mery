package com.maria.adoption.services;

import com.maria.adoption.AdoptionUserDetails;
import com.maria.adoption.entities.Role;
import com.maria.adoption.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdoptionUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public AdoptionUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public AdoptionUserDetails loadUserByUsername(String username) {
        System.out.println("Here!");
        User user = userService.findByUsername(username);
        Set<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        AdoptionUserDetails userDetails = new AdoptionUserDetails();
        userDetails.setAuthorities(authorities);
        userDetails.setUser(user);
//        return buildUserForAuthentication(user, authorities);
        return userDetails;
    }

    private Set<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return roles;
    }
//    private AdoptionUserDetails buildUserForAuthentication(User user, Set<GrantedAuthority> authorities) {
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                true, true, true, true, authorities);
//    }
}
