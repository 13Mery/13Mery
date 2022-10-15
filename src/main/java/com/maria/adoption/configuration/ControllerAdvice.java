package com.maria.adoption.configuration;

import com.maria.adoption.AdoptionUserDetails;
import com.maria.adoption.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model, Authentication authentication) {
        String name;
        if (authentication == null) {
            name = "";
        }
        else {
            AdoptionUserDetails userDetails = (AdoptionUserDetails) authentication.getPrincipal();
            if (userDetails.getUsername().equals("admin")) {
                System.out.println("admin");
                name = String.format("%s (Admin)", userDetails.getName());
            }
            else {
                System.out.println("Non-admin");
                name = userDetails.getName();
            }
        }
        model.addAttribute("loginName", name);
    }
}
