package com.mdscem.apitestframework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.fileinterpreter.FileInterpreter;
import com.mdscem.apitestframework.fileprocessor.filereader.FileConfigLoader;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCaseLoader;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import com.mdscem.apitestframework.requestprocessor.FrameworkAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiTest {
    private static ExtentReports extent;
    private static ExtentTest test;



    private List<TestCase> testCaseList = new ArrayList<>();
    private CoreFramework coreFramework;

    @BeforeSuite
    public void setUp() throws Exception {
        // Load framework from config
        coreFramework = loadFrameworkFromConfig();

        // Load and validate test cases
        testCaseList = loadAndValidateTestCases();
    }

    private CoreFramework loadFrameworkFromConfig() throws IOException {
        String frameworkType = FrameworkAdapter.loadFrameworkTypeFromConfig();
        switch (frameworkType.toLowerCase()) {
            case "restassured":
                return new RestAssuredCoreFramework();
            case "karate":
                return new KarateCoreFramework();
            default:
                throw new IllegalArgumentException("Unsupported framework type: " + frameworkType);
        }
    }

    private List<TestCase> loadAndValidateTestCases() throws Exception {
        FileConfigLoader configLoader = new FileConfigLoader("/home/hansakasudusinghe/Documents/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");
        List<String> testCaseFiles = extractTestCaseFiles(configLoader.getTestCaseFiles());

        for (String testCaseFile : testCaseFiles) {
            JsonNode testCases = new TestCaseLoader(testCaseFile).loadTestCases();
            if (testCases != null) {
                try {
                    JsonSchemaValidationWithJsonNode.validateFile(testCases);
                    testCaseList.addAll(FileInterpreter.interpret(testCases));
                    return testCaseList;
                } catch (IOException e) {
                    System.err.println("Validation error for " + testCaseFile + ": " + e.getMessage());
                }
            }
        }
        return testCaseList;
    }

    private List<String> extractTestCaseFiles(JsonNode testCaseFilesNode) {
        List<String> testCaseFiles = new ArrayList<>();
        if (testCaseFilesNode != null && testCaseFilesNode.isArray()) {
            Iterator<JsonNode> elements = testCaseFilesNode.elements();
            elements.forEachRemaining(node -> testCaseFiles.add(node.asText()));
        }
        return testCaseFiles;
    }

//    @Test
//    public void runTestCases() {
//        if (testCaseList.isEmpty()) {
//            System.err.println("No valid test cases available to run.");
//            return;
//        }
//
//        for (TestCase testCase : testCaseList) {
//            coreFramework.executeTestCase(testCase);  // Use CoreFramework interface
//        }
//    }

    @Factory
    public Object[] createTests() {

        System.out.println("Creating test cases...");
        if (testCaseList.isEmpty()) {
            System.err.println("No valid test cases available to run.");
            return new Object[0];
        }

        Object[] testMethods = new Object[testCaseList.size()];

        try{
            for (int i = 0; i < testCaseList.size(); i++) {
                System.out.println("Creating test case for: " + testCaseList.get(i));
                testMethods[i] = new TestCaseRunner(testCaseList.get(i), coreFramework);

                return testMethods;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}