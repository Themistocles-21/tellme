package com.cscd488.html.controller;

import com.cscd488.html.services.ServiceToggleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LanguageController {

    @Autowired
    private ServiceToggleService serviceToggleService;

    @PostMapping("/language")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request) {
        if (!serviceToggleService.isLanguageEnabled(lang)) {
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/home");
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/home");
    }
}