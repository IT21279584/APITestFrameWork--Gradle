package com.mdscem.apitestframework.fileprocessor.filereader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileConfigLoader {
    private JsonNode config;

    public FileConfigLoader(String configFilePath) {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get(configFilePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readTree(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: " + configFilePath);
        }
    }

    public JsonNode getTestCaseFiles() {
        if (config.isArray()) {
            return config;
        } else {
            throw new RuntimeException("Configuration root is not an array. Please provide an array of test case file paths.");
        }
    }
}
