package com.mjanvier.ariadnexttp.controller;

import com.mjanvier.ariadnexttp.service.social.SocialApiBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final SocialApiBinding socialApiBinding;

    @GetMapping("/")
    public String home(final Model model) {
        if (!socialApiBinding.isRealUser()) {
            return "badperson";
        }
        model.addAttribute("name", socialApiBinding.getUserName());
        return "idcheck";
    }

}
