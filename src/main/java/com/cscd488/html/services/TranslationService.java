package com.cscd488.html.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller

@Component

public class TranslationService {
    String content = "";
    FileService fileService = new FileService();

      public String translate(String textToTranslate) throws IOException, InterruptedException {

        String apiKey = System.getenv("OPENAI_API_KEY");

        HttpClient client = HttpClient.newHttpClient();

        String sendTextToTranslate =textToTranslate;

        String requestBody = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {"role": "system", "content": "Translate this in English"},
                    {"role": "user", "content": "%s"}
                  ]
                }
                """.formatted(sendTextToTranslate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
          fileService.writeToFile(responseBody,"jsonFileForAIAPI.txt");

          String fileData = fileService.readFromFile("jsonFileForAIAPI.txt");

          //  String content = "";
        Pattern pattern = Pattern.compile("\"content\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileData);
        if (matcher.find()) {
            content = matcher.group(1);
        }
          fileService.writeToFile("\n------------------------\nOriginal text:\n" + sendTextToTranslate + "\n\n\nEnglish translation:  \n" + content, "translation.txt");
        //  System.out.println("File written to: " + fileData);
        //System.out.println(response.body());

        return apiKey;
    }
}