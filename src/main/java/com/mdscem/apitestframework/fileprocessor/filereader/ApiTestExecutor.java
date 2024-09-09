package com.mdscem.apitestframework.fileprocessor.filereader;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTestExecutor {

    @Step("Execute Test Case: {testName}")
    public static void executeTestCase(JsonNode testCase) {
        String method = testCase.get("method").asText();
        String url = testCase.get("url").asText();
        int expectedStatusCode = testCase.get("expectedResponseCode").asInt();
        JsonNode requestBody = testCase.has("payload") ? testCase.get("payload") : null;
        JsonNode expectedResponseBody = testCase.get("expectedResponseBody");

        Response response = null;

        switch (method.toUpperCase()) {
            case "GET":
                response = given()
                        .when()
                        .get(url);
                break;
            case "POST":
                response = given()
                        .body(requestBody != null ? requestBody.toString() : "")
                        .when()
                        .post(url);
                break;
            case "PUT":
                response = given()
                        .body(requestBody != null ? requestBody.toString() : "")
                        .when()
                        .put(url);
                break;
            case "DELETE":
                response = given()
                        .when()
                        .delete(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        logResponse(response);
        validateResponse(response, expectedStatusCode, expectedResponseBody);
    }

    @Step("Log Response")
    private static void logResponse(Response response) {
        Allure.addAttachment("Response", response.asString());
    }

    @Step("Validate Response")
    private static void validateResponse(Response response, int expectedStatusCode, JsonNode expectedResponseBody) {
        response.then()
                .statusCode(expectedStatusCode)
                .body(equalTo(expectedResponseBody.toString()));
    }
}
