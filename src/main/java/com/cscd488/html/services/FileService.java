package com.cscd488.html.services;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    public String readFile(MultipartFile file) throws Exception {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
