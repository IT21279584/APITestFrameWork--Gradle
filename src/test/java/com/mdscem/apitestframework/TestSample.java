package com.mdscem.apitestframework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class TestSample {

    @Test
    public void testGetUser() {
        // Send GET request to fetch user details
        Response response = RestAssured
                .given()
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200) // Assert the status code
                .extract().response();

        // Extract data from the response
        String firstName = response.jsonPath().getString("data.first_name");
        String email = response.jsonPath().getString("data.email");

        // Validate the response data
        Assert.assertEquals(firstName, "Janet");
        Assert.assertEquals(email, "janet.weaver@reqres.in");
    }
}
