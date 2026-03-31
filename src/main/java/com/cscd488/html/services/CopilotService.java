package com.cscd488.html.services;
import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;

public class CopilotService {
    /**
     private final WebClient client = WebClient.builder()
     .baseUrl("https://api.githubcopilot.com/v1/chat/completions")
     .defaultHeader("Authorization", "Bearer YOUR_COPILOT_API_KEY")
     .build();

     public String translate(String text) {
     String prompt = "Translate this text to English:\n\n" + text;

     return client.post()
     .bodyValue("""
     {
     "model": "gpt-4o-mini",
     "messages": [
     {"role": "user", "content": "%s"}
     ]
     }
     """.formatted(prompt))
     .retrieve()
     .bodyToMono(String.class)
     .block();
     }*/

}
