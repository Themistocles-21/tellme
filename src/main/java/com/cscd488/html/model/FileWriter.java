package com.cscd488.html.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriter {

    public void writeToFile(String text, String fileName) throws IOException {
        Files.write(
                Paths.get(fileName),
                text.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}