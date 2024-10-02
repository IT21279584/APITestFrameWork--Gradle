package com.mdscem.apitestframework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;

public class RestAssuredCoreFramework implements CoreFramework {


    @Step("Executing test case")
    public void executeTestCase(TestCase testCase) {
        // Build the common request specification
        RequestSpecification requestSpec = buildRequestSpecification(testCase);

        // Execute the HTTP method and get the response
        Response response = executeHttpMethod(testCase.getMethod(), requestSpec, testCase.getUrl());

        // Attach request and response to Allure
        attachRequestAndResponseToAllure(testCase, response);

        // Validate the response
        validateResponse(testCase, response);
    }

    @Step("Building request specification")
    private RequestSpecification buildRequestSpecification(TestCase testCase) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + testCase.getAuthToken());

        if (testCase.getPayload() != null && !testCase.getPayload().isEmpty()) {
            requestSpec.body(testCase.getPayload());
        }

        return requestSpec;
    }

    @Step("Executing HTTP method: {method}")
    private Response executeHttpMethod(String method, RequestSpecification requestSpec, String url) {
        switch (method.toUpperCase()) {
            case "GET":
                return requestSpec.get(url);
            case "POST":
                return requestSpec.post(url);
            case "PUT":
                return requestSpec.put(url);
            case "DELETE":
                return requestSpec.delete(url);
            case "PATCH":
                return requestSpec.patch(url);
            default:
                throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
        }
    }

    // Attach the request and response details to Allure
    @Step("Attaching request and response to Allure")
    private void attachRequestAndResponseToAllure(TestCase testCase, Response response) {
        Allure.addAttachment("Request URL", testCase.getUrl());
        Allure.addAttachment("Request Method", testCase.getMethod());

        Allure.addAttachment("Response Status Code", String.valueOf(response.getStatusCode()));
        Allure.addAttachment("Response Body", response.getBody().asString());
    }

    // Validate the response
    @Step("Validating response")
    private void validateResponse(TestCase testCase, Response response) {
        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), testCase.getExpectedResponseCode(), "Status code mismatch");

        // Validate response body using JSONAssert if provided
        JsonNode expectedResponseBodyNode = testCase.getExpectedResponseBody();

        if (expectedResponseBodyNode != null && !expectedResponseBodyNode.isEmpty()) {
            try {
                // Convert JsonNode to String
                ObjectMapper objectMapper = new ObjectMapper();
                String expectedResponseBodyString = objectMapper.writeValueAsString(expectedResponseBodyNode);

                // Use JSONAssert to compare the response body
                JSONAssert.assertEquals(expectedResponseBodyString, response.getBody().asString(), JSONCompareMode.LENIENT);
            } catch (Exception e) {
                throw new RuntimeException("Failed to compare JSON response", e);
            }
        }
    }
}
