package com.mdscem.apitestframework;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.fileinterpreter.FileInterpreter;
import com.mdscem.apitestframework.fileprocessor.filereader.FileConfigLoader;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCaseLoader;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import com.mdscem.apitestframework.requestprocessor.FrameworkConfigLoader;
import com.mdscem.apitestframework.requestprocessor.KarateCoreFramework;
import com.mdscem.apitestframework.requestprocessor.RestAssuredCoreFramework;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiTest {

    private List<TestCase> testCaseList = new ArrayList<>();
    private CoreFramework coreFramework;

    @BeforeClass
    public void setUp() throws Exception {
        // Load framework from config
        coreFramework = loadFrameworkFromConfig();

        // Load and validate test cases
        loadAndValidateTestCases();
    }

    private CoreFramework loadFrameworkFromConfig() throws IOException {
        String frameworkType = FrameworkConfigLoader.loadFrameworkTypeFromConfig();
        switch (frameworkType.toLowerCase()) {
            case "restassured":
                return new RestAssuredCoreFramework();
            case "karate":
                return new KarateCoreFramework();
            default:
                throw new IllegalArgumentException("Unsupported framework type: " + frameworkType);
        }
    }

    private void loadAndValidateTestCases() throws Exception {
        FileConfigLoader configLoader = new FileConfigLoader("/home/kmedagoda/Downloads/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");
        List<String> testCaseFiles = extractTestCaseFiles(configLoader.getTestCaseFiles());

        for (String testCaseFile : testCaseFiles) {
            JsonNode testCases = new TestCaseLoader(testCaseFile).loadTestCases();
            if (testCases != null) {
                try {
                    JsonSchemaValidationWithJsonNode.validateFile(testCases);
                    testCaseList.addAll(FileInterpreter.interpret(testCases)); // Store validated test cases
                } catch (IOException e) {
                    System.err.println("Validation error for " + testCaseFile + ": " + e.getMessage());
                }
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

    @Test
    public void runTestCases() {
        if (testCaseList.isEmpty()) {
            System.err.println("No valid test cases available to run.");
            return;
        }

        for (TestCase testCase : testCaseList) {
            coreFramework.executeTestCase(testCase);  // Use CoreFramework interface
        }
    }
}
