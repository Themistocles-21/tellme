package com.cscd488.html.services;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class FileService {

    public String readFromFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }

    public void writeToFile(String text, String fileName) throws IOException {
        Files.write(
                Paths.get(fileName),
                text.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void append(String text, String fileName) throws IOException {
        Files.write(
                Path.of(fileName),
                text.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
    }
}