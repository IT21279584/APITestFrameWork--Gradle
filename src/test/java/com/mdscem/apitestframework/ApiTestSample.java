package com.mdscem.apitestframework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.aventstack.extentreports.Status.INFO;
import static io.restassured.RestAssured.given;

public class ApiTestSample {

    private static ExtentReports extent;
    private static ExtentTest test;
    public String Name = "Hansaka";
    private int age = 30;
    private String role = "Developer";

    private static String[][] data = {
            {"Header 1", "Header 2", "Header 3"},
            {"Row 1 Data 1", "Row 1 Data 2", "Row 1 Data 3"},
            {"Row 2 Data 1", "Row 2 Data 2", "Row 2 Data 3"},
            {"Row 3 Data 1", "Row 3 Data 2", "Row 3 Data 3"}
    };


    @BeforeAll
    public static void setup() {
        ExtentSparkReporter spark = new ExtentSparkReporter("build/ExtentReport.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Test Execution Report");
//        spark.config().setJs("/home/hansakasudusinghe/Documents/APITestFrameWork--Gradle/src/main/resources/config.js");
//        String customCss = " h1, h2, h3, h4, h5, h6 { font-size: 24px;} p { font-size: 24px;} table { font-size: 24px;} ";

        // Set the CSS for the report
//        spark.config().setCss(customCss);

//        spark.config().setJs("document.addEventListener('DOMContentLoaded', function() {\n" +
//                "        var systemHeading = document.querySelector('.sysenv-container .card-header p');\n" +
//                "        \n" +
//                "        console.log(systemHeading);\n" +
//                "\n" +
//                "        if (systemHeading && systemHeading.textContent.trim() === 'System/Environment') {\n" +
//                "            console.log('Changing text from Device to System Requirements');\n"+
//                "            systemHeading.textContent = 'System Requirements';\n" +
//                "        } else {\n" +
//                "            console.log('Element not found or text not matching');\n" +
//                "        }\n" +
//                "    });");

//        spark.config().setCss(".sysenv-container {\n" +
//                "    display: flex;\n" +
//                "    justify-content: right;\n" +
//                "    align-items: right;\n" +
//                "    ");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        logSystemInfo();
        logSystemInfo1();


    }

    private static void logSystemInfo() {
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String Name = "Hansaka";
        extent.setSystemInfo("Java Version", javaVersion);
        extent.setSystemInfo("OS", osName);
        extent.setSystemInfo("OS Version", osVersion);
        extent.setSystemInfo("Name", Name);


    }





    private static void logSystemInfo1() {
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        extent.setSystemInfo("Java Version", javaVersion);
        extent.setSystemInfo("OS", osName);
        extent.setSystemInfo("OS Version", osVersion);

    }

    private void initializeTest(String testName, String author) {
        test = extent.createTest(testName);
        test.assignAuthor(author);
        String queryParam = System.getProperty("edit");
        System.out.println(queryParam);
        if (queryParam != null) {
            test.log(INFO, "Query Parameter Passed from Command Line: edit = " + queryParam);
        } else {
            test.log(INFO, "No Query Parameter Passed from Command Line");
        }
    }

    private void logRequestResponse(String method, String url, Response response) {
        test.log(INFO, "Request Method: " + method);
        test.log(INFO, "Request URL: " + url);
        test.log(INFO, "Request Headers: " + response.getHeaders().toString());

        // Log Query Parameters if present
        if (method.equalsIgnoreCase("GET") && !response.getDetailedCookies().asList().isEmpty()) {
            test.log(INFO, "Query Parameters: " + response.getDetailedCookies().toString());
        }

        long responseTime = response.getTime();
        test.log(INFO, "Response Time: " + responseTime + " ms");
        test.log(INFO, "Response Status: " + response.getStatusLine());
        test.log(INFO, "Response Headers: " + response.getHeaders().toString());
        test.log(INFO, "Response Body: " + response.getBody().asString());
    }


    private void validateResponse(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        if (actualStatusCode == expectedStatusCode) {
            test.log(Status.PASS, "Status code validation passed: " + actualStatusCode);
        } else {
            test.log(Status.FAIL, "Status code validation failed: Expected = "
                    + expectedStatusCode + ", Actual = " + actualStatusCode);
            throw new AssertionError("Test failed due to status code mismatch: Expected = "
                    + expectedStatusCode + ", Actual = " + actualStatusCode);
        }
    }

    private void logCustomParameter(String key, String value) {
        test.log(INFO, key + ": " + value);
    }

    // Custom method to log the variables
    public static void addCustomMetadataSection(String sectionName) {
        // Add a custom section header (similar to a custom "page")
        test.log(INFO, "Custom Section: " + sectionName);

        // Add variables or metadata to the section
        test.log(INFO, "Variable: Name, Value: Hansaka");
        test.log(INFO, "Variable: Age, Value: 30");
        test.log(INFO, "Variable: Role, Value: Developer");

        // You can also make this more dynamic by accepting variable inputs
    }

    @Test
    public void testGetPost() {
        initializeTest("testGetPost", "John Doe");
        String url = "https://reqres.in/api/users/1";
        Response response = executeGetRequest(url);
        logRequestResponse("GET", url, response);
        validateResponse(response, 200);
    }

    @Test
    public void testGetPost2() {
        initializeTest("testGetPost", "John Doe");
        String url = "https://reqres.in/api/users/1";
        Response response = executeGetRequest(url);
        logRequestResponse("GET", url, response);
        validateResponse(response, 200);
    }

    @Test
    public void testGetPost3() {
        initializeTest("testGetPost", "John Doe");
        String url = "https://reqres.in/api/users/1";
        Response response = executeGetRequest(url);
        logRequestResponse("GET", url, response);
        validateResponse(response, 200);
    }

    @Test
    public void testGetUsersWithQueryParam() {
        initializeTest("testGetUsersWithQueryParam", "John Doe");
        String url = "https://reqres.in/api/users?page=2"; // Example URL with query parameter
        Response response = executeGetRequest(url);
        logRequestResponse("GET", url, response);
        validateResponse(response, 200);
        logCustomParameter("Header", response.getHeader("Content-Type"));

    }



    // HTTP methods for requests
    private Response executeGetRequest(String url) {
        test.assignCategory("GET");

        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(url);
    }

    private Response executePostRequest(String url, String requestBody) {
        test.assignCategory("POST");

        return given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(url);
    }

    private Response executePutRequest(String url, String requestBody) {
        test.assignCategory("PUT");

        return given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .put(url);
    }

    private Response executeDeleteRequest(String url) {
        test.assignCategory("DELETE");

        return given()
                .header("Content-Type", "application/json")
                .when()
                .delete(url);
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
        test.log(INFO, MarkupHelper.createTable(data));
        extent.flush();
    }
}
