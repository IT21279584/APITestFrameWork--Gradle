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

    // Load the test cases in the constructor or early
    public ApiTest() throws Exception {
        beforeSuite();
    }

    @Factory
    public Object[] createTests() {
        // If the test case list is not updated by now, log an error.
        if (testCaseList.isEmpty()) {
            System.err.println("No valid test cases available to run.");
            return new Object[0];
        }

        Object[] testMethods = new Object[testCaseList.size()];
        for (int i = 0; i < testCaseList.size(); i++) {
            testMethods[i] = new TestCaseRunner(testCaseList.get(i), coreFramework);
        }

        return testMethods;
    }

    public void beforeSuite() throws Exception {
        // Initialize reporting
        ExtentSparkReporter spark = new ExtentSparkReporter("build/Extent.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Test Execution Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        logSystemInfo();

        // Load framework
        coreFramework = loadFrameworkFromConfig();

        // Load and validate test cases
        loadAndValidateTestCases();

        // Log the final size of the test case list
        System.out.println("Final number of test cases loaded: " + testCaseList.size());
    }

    private CoreFramework loadFrameworkFromConfig() throws IOException {
        String frameworkType = FrameworkAdapter.loadFrameworkTypeFromConfig();
        System.out.println("Framework loaded from config: " + frameworkType);

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
        FileConfigLoader configLoader = new FileConfigLoader("/home/kmedagoda/Documents/Kavinda Final/APITestFrameWork--Gradle/src/main/resources/fileconfig.json");

        // Log test case files
        System.out.println("Loading test case files from config...");
        List<String> testCaseFiles = extractTestCaseFiles(configLoader.getTestCaseFiles());
        System.out.println("Test case files found: " + testCaseFiles);

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

    private static void logSystemInfo() {
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        extent.setSystemInfo("Java Version", javaVersion);
        extent.setSystemInfo("OS", osName);
        extent.setSystemInfo("OS Version", osVersion);
        System.out.println("System Info: Java Version - " + javaVersion + ", OS - " + osName + ", OS Version - " + osVersion);
    }
}
