package com.mdscem.apitestframework.validatetestcase;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.fileinterpreter.FileInterpreter;
import com.mdscem.apitestframework.fileprocessor.filereader.FileConfigLoader;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCaseLoader;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mdscem.apitestframework.constants.Constant.MULTIPLE_FILE_PATH;

public class ValidateTestCase {
    public void loadAndValidateTestCases(List<TestCase> testCaseList) throws Exception {
        FileConfigLoader configLoader = new FileConfigLoader(MULTIPLE_FILE_PATH);

        // Log test case files
        List<String> testCaseFiles = extractTestCaseFiles(configLoader.getTestCaseFiles());

        // Load each test case file
        for (String testCaseFile : testCaseFiles) {
            System.out.println("Loading test case file: " + testCaseFile);

            JsonNode testCases = new TestCaseLoader(testCaseFile).loadTestCases();

            if (testCases != null) {
                System.out.println("Test cases loaded successfully from file: " + testCaseFile);
                try {
                    JsonSchemaValidationWithJsonNode.validateFile(testCases);
                    System.out.println("Schema validation passed for file: " + testCaseFile);
                    List<TestCase> interpretedTestCases = FileInterpreter.interpret(testCases);
                    testCaseList.addAll(interpretedTestCases);

                    // Log how many test cases were added
                    System.out.println("Added " + interpretedTestCases.size() + " test cases from " + testCaseFile);
                } catch (IOException e) {
                    System.err.println("Validation error for " + testCaseFile + ": " + e.getMessage());
                }
            } else {
                System.err.println("No test cases found in file: " + testCaseFile);
            }
        }
    }


    private List<String> extractTestCaseFiles(JsonNode testCaseFilesNode) {
        List<String> testCaseFiles = new ArrayList<>();
        if (testCaseFilesNode != null && testCaseFilesNode.isArray()) {
            Iterator<JsonNode> elements = testCaseFilesNode.elements();
            elements.forEachRemaining(node -> testCaseFiles.add(node.asText()));
        }
        return testCaseFiles;
    }
}
