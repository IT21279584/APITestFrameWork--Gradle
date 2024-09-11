package com.mdscem.apitestframework;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.fileinterpreter.FileInterpreter;
import com.mdscem.apitestframework.fileprocessor.filereader.FileConfigLoader;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCaseLoader;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ApiTestMain {

    public static void main(String[] args) throws Exception {
        FileConfigLoader configLoader = new FileConfigLoader("/home/hansakasudusinghe/Documents/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");

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
                    FileInterpreter.interpret(testCases);
                } catch (Exception e) {
//                    Allure.step("Test case file: " + testCaseFile + " failed validation", Status.FAILED);
//                    attachTestCaseDetails(testCases);
                    e.printStackTrace();
                }
            } else {
                System.err.println("Failed to load test cases from file: " + testCaseFile);
            }
        }
    }
}
