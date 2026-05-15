package com.cscd488.html.services;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TranslationService {

    private final FileService fileService;

    public TranslationService(FileService fileService) {
        this.fileService = fileService;
    }

    public String translate(String textToTranslate) throws IOException, InterruptedException {
        if (textToTranslate == null || textToTranslate.trim().isEmpty()) {
            return "";
        }

        String apiKey = System.getenv("OPENAI_API_KEY");

        HttpClient client = HttpClient.newHttpClient();

        String requestBody = """
                {
                  "model": "gpt-4o-mini",
                  "messages": [
                    {"role": "system", "content": "Translate this to English"},
                    {"role": "user", "content": "%s"}
                  ]
                }
                """.formatted(textToTranslate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        fileService.writeToFile(responseBody, "jsonFileForAIAPI.txt");

        String fileData = fileService.readFromFile("jsonFileForAIAPI.txt");

        Pattern pattern = Pattern.compile("\"content\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileData);
        String content = "";
        if (matcher.find()) {
            content = matcher.group(1);
        }

        fileService.writeToFile("\n------------------------\nOriginal text:\n" + textToTranslate + "\n\n\nEnglish translation:\n" + content, "translation.txt");

        return content;
    }
}