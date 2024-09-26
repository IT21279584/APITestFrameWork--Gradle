package com.mdscem.apitestframework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {

    @BeforeClass
    public void setup() {
        // Base URI for Rest Assured
        RestAssured.baseURI = "https://example.com/api";
    }

    @Test
    public void testSuccessfulLogin() {
        // JSON body for login request
        String requestBody = "{ \"username\": \"testUser\", \"password\": \"testPass\" }";

        // Perform POST request to login
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/login");

        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 for successful login");

        // Validate that the response contains a token
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Token should not be null after successful login");
    }

    @Test
    public void testInvalidLogin() {
        // JSON body with invalid credentials
        String requestBody = "{ \"username\": \"invalidUser\", \"password\": \"wrongPass\" }";

        // Perform POST request to login
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/login");

        // Validate response status code
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 for invalid login");

        // Validate error message in response
        String errorMessage = response.jsonPath().getString("error");
        Assert.assertEquals(errorMessage, "Invalid credentials", "Expected error message for invalid login");
    }
}
