package com.mdscem.apitestframework;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.filereader.FileConfigLoader;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCaseLoader;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiTest {

    private List<String> testCaseFiles;

    @BeforeClass
    public void setUp() throws Exception {
        // Initialize the config loader and load the test case files before tests run
        FileConfigLoader configLoader = new FileConfigLoader("/home/kmedagoda/Downloads/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");

        JsonNode testCaseFilesNode = configLoader.getTestCaseFiles();
        testCaseFiles = new ArrayList<>();
        if (testCaseFilesNode != null && testCaseFilesNode.isArray()) {
            Iterator<JsonNode> elements = testCaseFilesNode.elements();
            while (elements.hasNext()) {
                testCaseFiles.add(elements.next().asText());
            }
        }
    }

    @Test
    public void runTestCases() {
        // Iterate over the test case files and validate each one
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
