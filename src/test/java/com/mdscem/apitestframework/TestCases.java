//package com.mdscem.apitestframework;
//
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static com.mdscem.apitestframework.ApiTest.extent;
//
//public class TestCases {
//
//    ApiTest apiTest = new ApiTest(); // Use ApiTest methods for HTTP requests
//    private static ExtentTest test;
//
//    // POST Requests (10 Test Cases)
//    @Test
//    public void testCreateUser1() {
//        // Create a test entry for POST request
//        test = extent.createTest("testCreateUser1");
//        test.assignCategory("Regression", "API Tests");
//        test.assignAuthor("Hansaka");
//
//        // Log request details
//        logRequest("POST", "https://reqres.in/api/users");
//
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Software Engineer\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//        // Log response time
//        logResponseTime(response);
//        // Attach response body to the report
//        attachResponseBodyToReport(response);
//
//    }
//
//    @Test
//    public void testCreateUser2() {
//        String requestBody = "{ \"name\": \"Jane\", \"job\": \"Manager\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//    }
//
//    @Test
//    public void testCreateUserFail1() {
//        String requestBody = ""; // Empty request body
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testCreateUserFail2() {
//        String requestBody = "{ \"name\": \"John\" }"; // Missing job field
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testCreateUserSkipped1() {
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Software Engineer\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testCreateUserSkipped2() {
//        String requestBody = "{ \"name\": \"Jane\", \"job\": \"Manager\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//    }
//
//    @Test
//    public void testCreateUser3() {
//        String requestBody = "{ \"name\": \"Sam\", \"job\": \"HR Manager\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//    }
//
//    @Test
//    public void testCreateUserFail3() {
//        String requestBody = "{ \"job\": \"HR Manager\" }"; // Missing name field
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testCreateUser4() {
//        String requestBody = "{ \"name\": \"Emily\", \"job\": \"Analyst\" }";
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 201);
//    }
//
//    @Test
//    public void testCreateUserFail4() {
//        String requestBody = "{ \"name\": \"Emily\" }"; // Missing job field
//        Response response = apiTest.executePostRequest("https://reqres.in/api/users", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    // GET Requests (10 Test Cases)
//    @Test
//    public void testGetUser1() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/1");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testGetUser2() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/2");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testGetUserFail1() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/999"); // Non-existent user
//        apiTest.validateStatusCode(response, 404);
//    }
//
//    @Test
//    public void testGetUserFail2() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/abc"); // Invalid user ID
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testGetUserSkipped1() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/1");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testGetUserSkipped2() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/2");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testGetUser3() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/3");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testGetUserFail3() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/invalid"); // Invalid ID
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testGetUser4() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/4");
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testGetUserFail4() {
//        Response response = apiTest.executeGetRequest("https://reqres.in/api/users/987"); // Non-existent user
//        apiTest.validateStatusCode(response, 404);
//    }
//
//    // PUT Requests (10 Test Cases)
//    @Test
//    public void testUpdateUser1() {
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Product Manager\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/1", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testUpdateUser2() {
//        String requestBody = "{ \"name\": \"Jane\", \"job\": \"HR Manager\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/2", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testUpdateUserFail1() {
//        String requestBody = "{ \"name\": \"John\" }"; // Missing job field
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/1", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testUpdateUserFail2() {
//        String requestBody = "{ \"job\": \"HR Manager\" }"; // Missing name field
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/2", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testUpdateUserSkipped1() {
//        String requestBody = "{ \"name\": \"John\", \"job\": \"Product Manager\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/1", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testUpdateUserSkipped2() {
//        String requestBody = "{ \"name\": \"Jane\", \"job\": \"HR Manager\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/2", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testUpdateUser3() {
//        String requestBody = "{ \"name\": \"Sam\", \"job\": \"Analyst\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/3", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testUpdateUserFail3() {
//        String requestBody = ""; // Empty request body
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/3", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testUpdateUser4() {
//        String requestBody = "{ \"name\": \"Emily\", \"job\": \"Consultant\" }";
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/4", requestBody);
//        apiTest.validateStatusCode(response, 200);
//    }
//
//    @Test
//    public void testUpdateUserFail4() {
//        String requestBody = "{ \"name\": \"Emily\" }"; // Missing job field
//        Response response = apiTest.executePutRequest("https://reqres.in/api/users/4", requestBody);
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    // DELETE Requests (10 Test Cases)
//    @Test
//    public void testDeleteUser1() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/1");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Test
//    public void testDeleteUser2() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/2");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Test
//    public void testDeleteUserFail1() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/999"); // Non-existent user
//        apiTest.validateStatusCode(response, 404);
//    }
//
//    @Test
//    public void testDeleteUserFail2() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/abc"); // Invalid user ID
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testDeleteUserSkipped1() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/1");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Disabled("Skipping this test case")
//    @Test
//    public void testDeleteUserSkipped2() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/2");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Test
//    public void testDeleteUser3() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/3");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Test
//    public void testDeleteUserFail3() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/invalid"); // Invalid ID
//        apiTest.validateStatusCode(response, 400);
//    }
//
//    @Test
//    public void testDeleteUser4() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/4");
//        apiTest.validateStatusCode(response, 204);
//    }
//
//    @Test
//    public void testDeleteUserFail4() {
//        Response response = apiTest.executeDeleteRequest("https://reqres.in/api/users/987"); // Non-existent user
//        apiTest.validateStatusCode(response, 404);
//    }
//
//    public void logRequest(String method, String url) {
//        // Log request details
//        test.log(Status.INFO, "Request Method: " + method);
//        test.log(Status.INFO, "Request URL: " + url);
//    }
//
//    public void logResponseTime(Response response) {
//        // Log response time
//        long responseTime = response.getTime();
//        test.log(Status.INFO, "Response Time: " + responseTime + " ms");
//    }
//
//
//
//
//    public void attachResponseBodyToReport(Response response) {
//        // Attach response body to the Extent Report
//        String responseBody = response.getBody().asString();
//        test.log(Status.INFO, "Response Body: " + responseBody);
//    }
//
//    @AfterEach
//    public void afterEachTest() {
//        // Custom actions after each test
//        test.log(Status.INFO, "Test Completed");
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        // Flush the ExtentReports after all tests
//        extent.flush();
//    }
//
//}
