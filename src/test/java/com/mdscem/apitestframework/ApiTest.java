package com.mdscem.apitestframework;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.frameworkconfig.FrameworkLoader;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import com.mdscem.apitestframework.validatetestcase.ValidateTestCase;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aventstack.extentreports.Status.INFO;
import static com.mdscem.apitestframework.ReportGenerator.logSystemInfo;

public class ApiTest {

    private static ExtentReports extent;
    public static ExtentTest test;
    private static List<TestCase> testCaseList = new ArrayList<>();
    private static CoreFramework coreFramework;

    @BeforeAll
    public static void beforeAll() throws Exception {
        ExtentSparkReporter spark = new ExtentSparkReporter("build/Extent.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Test Execution Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);



        FrameworkLoader frameworkLoader = new FrameworkLoader();
        coreFramework = frameworkLoader.loadFrameworkFromConfig();

        ValidateTestCase validateTestCase = new ValidateTestCase();
        validateTestCase.loadAndValidateTestCases(testCaseList);

        System.out.println("Final number of test cases loaded: " + testCaseList.size());
    }

    @TestFactory
    public Stream<DynamicTest> createTests() {
        if (testCaseList.isEmpty()) {
            System.err.println("No valid test cases available to run.");
            return Stream.empty();
        }

        return testCaseList.stream().map(this::createDynamicTest);
    }

    private DynamicTest createDynamicTest(TestCase testCase) {
        return DynamicTest.dynamicTest(testCase.getTestName(), () -> {
            try {
                test = extent.createTest(testCase.getTestName());
                test.log(Status.INFO, "Request Method: " +testCase.getMethod());
                test.log(Status.INFO, "Request Url: " +testCase.getUrl());
                test.assignCategory(testCase.getMethod());


                TestCaseRunner runner = new TestCaseRunner(testCase, coreFramework);
                runner.runTestCase();

                test.pass("Test passed successfully");

            } catch (AssertionError | Exception e) {
                test.fail("Test failed: " + e.getMessage());
                throw e;
            }
        });
    }
    public void logRequestResponse(Response response){
        long responseTime = response.getTime();
        test.log(INFO, "Response Time: " + responseTime + " ms");
        test.log(INFO, "Response Status: " + response.getStatusLine());
        test.log(INFO, "Response Headers: " + response.getHeaders().toString());
        test.log(INFO, "Response Body: " + response.getBody().asString());
    }

    @AfterAll
    public static void afterAll() {
        extent.flush();
    }
}

