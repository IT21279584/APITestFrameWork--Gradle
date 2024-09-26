package com.mdscem.apitestframework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class


ApiTestSample {

    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeAll
    public static void setup() {
        ExtentSparkReporter spark = new ExtentSparkReporter("build/ExtentReport.html");
        spark.config().setReportName("API Test Report");
        spark.config().setDocumentTitle("Test Execution Report");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        logSystemInfo();
    }

    private static void logSystemInfo() {
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
    }

    private void logRequestResponse(String method, String url, Response response) {
        test.log(Status.INFO, "Request Method: " + method);
        test.log(Status.INFO, "Request URL: " + url);
        test.log(Status.INFO, "Request Headers: " + response.getHeaders().toString());

        // Log Query Parameters if present
        if (method.equalsIgnoreCase("GET") && !response.getDetailedCookies().asList().isEmpty()) {
            test.log(Status.INFO, "Query Parameters: " + response.getDetailedCookies().toString());
        }

        long responseTime = response.getTime();
        test.log(Status.INFO, "Response Time: " + responseTime + " ms");
        test.log(Status.INFO, "Response Status: " + response.getStatusLine());
        test.log(Status.INFO, "Response Headers: " + response.getHeaders().toString());
        test.log(Status.INFO, "Response Body: " + response.getBody().asString());
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
        test.log(Status.INFO, key + ": " + value);
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


//    @Test
//    public void testCreateUser() {
//        initializeTest("testCreateUser", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Software Engineer\" }";
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 201);
//    }
//
//    @Test
//    public void testGetNonExistentUser() {
//        initializeTest("testGetNonExistentUser", "Hansaka");
//        String url = "https://reqres.in/api/users/9999";
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testUpdateUser() {
//        initializeTest("testUpdateUser", "Hansaka");
//        String url = "https://reqres.in/api/users/2";
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Senior Software Engineer\" }";
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testDeleteUser() {
//        initializeTest("testDeleteUser", "Hansaka");
//        String url = "https://reqres.in/api/users/2";
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 204);
//    }
//
//    @Test
//    public void testUpdateNonExistentUser() {
//        initializeTest("testUpdateNonExistentUser", "Hansaka");
//        String url = "https://reqres.in/api/users/9999";
//        String requestBody = "{ \"name\": \"Nonexistent User\", \"job\": \"Tester\" }";
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testCreateUserWithInvalidData() {
//        initializeTest("testCreateUserWithInvalidData", "Pawan");
//        String url = "https://reqres.in/api/unknown";
//        String requestBody = "{ \"name\": \"\", \"job\": \"\" }"; // Invalid data
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testSkipTest() {
//        initializeTest("testSkipTest", "Pawan");
//        assumeTrue(false, "Skipping this test as the condition is not met.");
//        test.log(Status.SKIP, "This test was skipped as expected.");
//    }
//
//    @Test
//    public void testDeleteNonExistentUser() {
//        initializeTest("testDeleteNonExistentUser", "Hansaka");
//        String url = "https://reqres.in/api/unknown/9999";
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        initializeTest("testGetAllUsers", "Pawan");
//        String url = "https://reqres.in/api/unknown?page=2";
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    // New Test Methods
//
//    @Test
//    public void testCreateUserWithValidData() {
//        initializeTest("testCreateUserWithValidData", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"Alice\", \"job\": \"Developer\" }";
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 201);
//    }
//
//    @Test
//    public void testUpdateUserWithInvalidData() {
//        initializeTest("testUpdateUserWithInvalidData", "Kaushalya");
//        String url = "https://reqres.in/api/unknown/2";
//        String requestBody = "{ \"name\": \"\", \"job\": \"\" }"; // Invalid data
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testGetUser() {
//        initializeTest("testGetUser", "Hansaka");
//        String url = "https://reqres.in/api/unknown/2";
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testCreateUserWithMissingField() {
//        initializeTest("testCreateUserWithMissingField", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"job\": \"Developer\" }"; // Missing name
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testDeleteUserNotFound() {
//        initializeTest("testDeleteUserNotFound", "Kaushalya");
//        String url = "https://reqres.in/api/unknown/9999";
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testCreateUserWithEmptyJson() {
//        initializeTest("testCreateUserWithEmptyJson", "Hansaka");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{}"; // Empty JSON
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testGetAllUsersWithQueryParam() {
//        initializeTest("testGetAllUsersWithQueryParam", "Kavinda");
//        String url = "https://reqres.in/api/users?delay=3";
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testCreateUserWithNullName() throws InterruptedException {
//        Thread.sleep(5000);
//        initializeTest("testCreateUserWithNullName", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": null, \"job\": \"Developer\" }"; // Invalid data
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testCreateUserWithJobOnly() {
//        initializeTest("testCreateUserWithJobOnly", "Hansaka");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"job\": \"Developer\" }"; // Missing name
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testCreateUserDuplicateEntry() {
//        initializeTest("testCreateUserDuplicateEntry", "Kaushalya");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Developer\" }"; // Duplicate entry
//        Response response1 = executePostRequest(url, requestBody); // First entry
//        Response response2 = executePostRequest(url, requestBody); // Duplicate
//        logRequestResponse("POST", url, response1);
//        logRequestResponse("POST", url, response2);
//        validateResponse(response1, 201);
//        validateResponse(response2, 400); // Expecting failure for duplicate
//    }
//
//    @Test
//    public void testCreateUserWithSpecialChars() throws InterruptedException {
//        Thread.sleep(5000);
//        initializeTest("testCreateUserWithSpecialChars", "Hansaka");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"John@!$\", \"job\": \"Developer\" }"; // Special characters
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 201);
//    }
//
//    @Test
//    public void testCreateUserWithValidData2() {
//        initializeTest("testCreateUserWithValidData2", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"Bob\", \"job\": \"Designer\" }";
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 201);
//    }
//    @Test
//    public void testGetUserWithInvalidId() {
//        initializeTest("testGetUserWithInvalidId", "Kaushalya");
//        String url = "https://reqres.in/api/users/99999"; // Non-existent user
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testGetAllUsersWithDelay() {
//        initializeTest("testGetAllUsersWithDelay", "Hansaka");
//        String url = "https://reqres.in/api/users?delay=3"; // Testing delay
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testGetSingleUser() {
//        initializeTest("testGetSingleUser", "Kavinda");
//        String url = "https://reqres.in/api/users/2"; // Valid user
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testGetUsersWithPagination() {
//        initializeTest("testGetUsersWithPagination", "Kaushalya");
//        String url = "https://reqres.in/api/users?page=2"; // Paginated users
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 200);
//    }
//
//    @Test
//    public void testGetUserNotFound() {
//        initializeTest("testGetUserNotFound", "Hansaka");
//        String url = "https://reqres.in/api/users/123456"; // Non-existent user
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 404);
//    }
//    @Test
//    public void testUpdateUserWithEmptyBody() {
//        initializeTest("testUpdateUserWithEmptyBody", "Kavinda");
//        String url = "https://reqres.in/api/users/2";
//        String requestBody = "{}"; // Empty body
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 400);
//    }
//
//    @Test
//    public void testUpdateNonExistentUserWithInvalidData() {
//        initializeTest("testUpdateNonExistentUserWithInvalidData", "Hansaka");
//        String url = "https://reqres.in/api/users/9999"; // Non-existent user
//        String requestBody = "{ \"name\": \"Updated User\", \"job\": \"Developer\" }";
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testUpdateUserWithNullJob() {
//        initializeTest("testUpdateUserWithNullJob", "Kaushalya");
//        String url = "https://reqres.in/api/users/2";
//        String requestBody = "{ \"name\": \"John\", \"job\": null }"; // Invalid job
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 400);
//    }
//    @Test
//    public void testDeleteUserNotFound2() throws InterruptedException {
//        Thread.sleep(5000);
//        initializeTest("testDeleteUserNotFound2", "Kavinda");
//        String url = "https://reqres.in/api/users/9999"; // Non-existent user
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 404);
//    }
//
//    @Test
//    public void testDeleteUserWithInvalidId() {
//        initializeTest("testDeleteUserWithInvalidId", "Hansaka");
//        String url = "https://reqres.in/api/users/999"; // Non-existent user
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 204);
//    }
//
//    @Test
//    public void testDeleteExistingUser() throws InterruptedException {
//        Thread.sleep(5000);
//        initializeTest("testDeleteExistingUser", "Kaushalya");
//        String url = "https://reqres.in/api/users/2"; // Valid user
//        Response response = executeDeleteRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 204);
//    }
//
//    @Test
//    public void testDeleteUserWithIncorrectMethod() {
//        initializeTest("testDeleteUserWithIncorrectMethod", "Kavinda");
//        String url = "https://reqres.in/api/users/2"; // Trying DELETE on GET
//        Response response = executeGetRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 200); // Expected to fail
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testGetUserWithInvalid() {
//        initializeTest("testGetUserWithInvalidId", "Kavinda");
//        String url = "https://reqres.in/api/users/9999"; // Non-existent user
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 404);
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testCreateUserWithInvalid() {
//        initializeTest("testCreateUserWithInvalidData", "Hansaka");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"\", \"job\": \"Developer\" }"; // Invalid name
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testUpdateUserWithInvalidId() {
//        initializeTest("testUpdateUserWithInvalidId", "Kaushalya");
//        String url = "https://reqres.in/api/users/9999"; // Non-existent user
//        String requestBody = "{ \"name\": \"Updated User\", \"job\": \"Developer\" }";
//        Response response = executePutRequest(url, requestBody);
//        logRequestResponse("PUT", url, response);
//        validateResponse(response, 404);
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testCreateDuplicateUser() {
//        initializeTest("testCreateDuplicateUser", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Developer\" }"; // Duplicate entry
//        Response response1 = executePostRequest(url, requestBody); // First entry
//        Response response2 = executePostRequest(url, requestBody); // Duplicate
//        logRequestResponse("POST", url, response1);
//        logRequestResponse("POST", url, response2);
//        validateResponse(response1, 201);
//        validateResponse(response2, 400); // Expecting failure for duplicate
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testGetUsersWithMissingParameters() {
//        initializeTest("testGetUsersWithMissingParameters", "Hansaka");
//        String url = "https://reqres.in/api/users?page"; // Missing page parameter
//        Response response = executeGetRequest(url);
//        logRequestResponse("GET", url, response);
//        validateResponse(response, 400);
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testDeleteUserWithInvalidMethod() {
//        initializeTest("testDeleteUserWithInvalidMethod", "Kaushalya");
//        String url = "https://reqres.in/api/users/2"; // Trying DELETE on GET
//        Response response = executeGetRequest(url);
//        logRequestResponse("DELETE", url, response);
//        validateResponse(response, 200); // Expected to fail
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }
//
//    @Test
//    @Ignore("Skipping test for demonstration")
//    public void testCreateUserWithoutRequiredFields() {
//        initializeTest("testCreateUserWithoutRequiredFields", "Kavinda");
//        String url = "https://reqres.in/api/users";
//        String requestBody = "{ \"job\": \"Developer\" }"; // Missing required name field
//        Response response = executePostRequest(url, requestBody);
//        logRequestResponse("POST", url, response);
//        validateResponse(response, 400);
//        test.log(Status.SKIP, "This test was skipped as expected.");
//
//    }


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
            test.log(Status.INFO, "Test Completed");
        }else{
            extent.flush();
        }
    }

    @AfterAll
    public static void tearDownAll() {
        extent.flush();
    }
}
