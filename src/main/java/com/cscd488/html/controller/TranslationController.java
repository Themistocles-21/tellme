package com.cscd488.html.controller;

import com.cscd488.html.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TranslationController {

    @Autowired
    private FileService fileService;

    @GetMapping("/translate")
    public String showUploadPage() {
        return "translate";
    }

    @PostMapping("/translate")
    public String translateFile(MultipartFile file, Model model) throws Exception {
        String originalText = fileService.readFile(file);
        // String translatedText = translationService.translateToEnglish(originalText);

        model.addAttribute("original", originalText);
        // model.addAttribute("translated", translatedText);

        return "result";
    }
}