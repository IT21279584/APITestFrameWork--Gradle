package com.mdscem.apitestframework.requestprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FrameworkAdapter {

    public static String loadFrameworkTypeFromConfig() throws IOException {
        // Load the JSON configuration file
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile = new File("/home/hansakasudusinghe/Documents/APITestFrameWork--Gradle/src/main/resources/framework-config.json");

        if (!configFile.exists()) {
            throw new IOException("Configuration file not found: " + configFile.getAbsolutePath());
        }

        // Parse the JSON and extract the framework type
        JsonNode configNode = objectMapper.readTree(configFile);
        String frameworkType = configNode.get("framework").asText();

        return frameworkType;
    }

}

