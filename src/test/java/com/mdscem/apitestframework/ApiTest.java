package com.mdscem.apitestframework;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @Test
    public void testGetPost() {
        // Step 1: Log the request
        logRequest("GET", "https://reqres.in/api/users/1");

        // Step 2: Send GET request and log response
        Response response = executeGetRequest("https://reqres.in/api/users/1");

        // Step 3: Validate and log the response status code
        validateStatusCode(response, 200);

        // Step 4: Attach response body to the Allure report
        attachResponseBodyToAllure(response);
    }

    @Step("Execute GET request to {url}")
    public Response executeGetRequest(String url) {
        return given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    @Step("Log request: {method} {url}")
    public void logRequest(String method, String url) {
        Allure.addAttachment("Request Method", method);
        Allure.addAttachment("Request URL", url);
    }

    @Step("Validate response status code: Expected = {expectedStatusCode}")
    public void validateStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
        Allure.addAttachment("Actual Status Code", String.valueOf(response.getStatusCode()));
    }

    @Step("Attach response body to Allure report")
    public void attachResponseBodyToAllure(Response response) {
        String responseBody = response.getBody().asString();
        Allure.addAttachment("Response Body", responseBody);
    }
}
