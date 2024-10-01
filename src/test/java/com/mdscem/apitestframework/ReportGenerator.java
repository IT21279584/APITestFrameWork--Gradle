package com.mdscem.apitestframework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.mdscem.apitestframework.frameworkconfig.FrameworkLoader;
import com.mdscem.apitestframework.validatetestcase.ValidateTestCase;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.aventstack.extentreports.Status.INFO;
import static io.restassured.RestAssured.given;

public class ReportGenerator {

    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeAll
    public static void beforeAll() throws Exception {
        ExtentSparkReporter spark = new ExtentSparkReporter("build/ExtentReport.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Test Execution Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);



        FrameworkLoader frameworkLoader = new FrameworkLoader();

        ValidateTestCase validateTestCase = new ValidateTestCase();

    }

    public static void logSystemInfo() {
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        extent.setSystemInfo("Java Version", javaVersion);
        extent.setSystemInfo("OS", osName);
        extent.setSystemInfo("OS Version", osVersion);
    }


    private void initializeTest(String testName) {
        test = extent.createTest(testName);
    }

    private void logRequestResponse(String method, String url, Response response) {
        System.out.println("My Response" + response);
        test.log(INFO, "Request Method: " + method);
        test.log(INFO, "Request URL: " + url);
        test.log(INFO, "Request Headers: " + response.getHeaders().toString());

        long responseTime = response.getTime();
        test.log(INFO, "Response Time: " + responseTime + " ms");
        test.log(INFO, "Response Status: " + response.getStatusLine());
        test.log(INFO, "Response Headers: " + response.getHeaders().toString());
        test.log(INFO, "Response Body: " + response.getBody().asString());
    }


    // Custom method to log the variables





    @Test
    public void testGetPost3() {
        initializeTest("testGetPost");
        String url = "https://reqres.in/api/users/1";
        Response response = executeGetRequest(url);
        logRequestResponse("GET", url, response);
    }




    // HTTP methods for requests
    private Response executeGetRequest(String url) {
        test.assignCategory("GET");

        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(url);
    }


    @AfterEach
    public void tearDown() {
        // Log the test completion status
        if (test.getStatus() != Status.SKIP) {
            test.info(MarkupHelper.createLabel("Test Completed", ExtentColor.GREEN));
        }else{
            extent.flush();
        }
    }

    @AfterAll
    public static void tearDownAll() {
        extent.flush();
    }
}
