package com.cscd488.html;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication

public class SpringbootHtmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHtmlApplication.class, args);
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }
}