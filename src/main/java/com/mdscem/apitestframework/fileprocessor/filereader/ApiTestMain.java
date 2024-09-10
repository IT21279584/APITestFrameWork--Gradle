package com.mdscem.apitestframework.fileprocessor.filereader;

import com.fasterxml.jackson.databind.JsonNode;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;
import io.qameta.allure.Attachment;


public class ApiTestMain {

    public static void main(String[] args) throws Exception {
        FileConfigLoader configLoader = new FileConfigLoader("/home/kmedagoda/Downloads/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");

        JsonNode testCaseFilesNode = configLoader.getTestCaseFiles();
        List<String> testCaseFiles = new ArrayList<>();
        if (testCaseFilesNode != null && testCaseFilesNode.isArray()) {
            Iterator<JsonNode> elements = testCaseFilesNode.elements();
            while (elements.hasNext()) {
                testCaseFiles.add(elements.next().asText());
            }
        }

        for (String testCaseFile : testCaseFiles) {
            TestCaseLoader testCaseLoader = new TestCaseLoader(testCaseFile);
            JsonNode testCases = testCaseLoader.loadTestCases();
            if (testCases != null) {
                System.out.println("Load Test case: " + testCases.toPrettyString());
                try {
                    JsonSchemaValidationWithJsonNode.validateFile(testCases);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Failed to load test cases from file: " + testCaseFile);
            }
        }
    }
}