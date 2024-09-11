package com.mdscem.apitestframework.fileprocessor.fileinterpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileInterpreter {

    public static List<TestCase> interpret(JsonNode validatedJson) {
        List<TestCase> testCases = new ArrayList<>();

        // 1. Parse JSON Object
        // Check if the validated JSON is an array of test cases
        if (validatedJson.isArray()) {
            for (JsonNode testNode : validatedJson) {
                TestCase testCase = new TestCase();

                // 2. Data Mapping
                // Map each field from the JSON node to the TestCase object
                try {
                    testCase.setId(testNode.path("id").asText());
                    testCase.setUrl(testNode.path("url").asText());
                    testCase.setMethod(testNode.path("method").asText());
                    testCase.setPayload(testNode.path("payload"));
                    testCase.setExpectedResponseCode(testNode.path("expectedResponseCode").asText());
                    testCase.setTestName(testNode.path("testName").asText());
                    testCase.setExpectedResponseBody(testNode.path("expectedResponseBody"));
                    testCase.setRequiresAuthentication(testNode.path("requiresAuthentication").asText());
                    testCase.setAuthToken(testNode.path("authToken").asText());
                    testCase.setPriority(testNode.path("priority").asText());

                    // 3. Action Execution: Add the test case to the list
                    testCases.add(testCase);
                    System.out.println(testCases);
                } catch (Exception e) {
                    System.err.println("Error while interpreting test case: " + e.getMessage());
                }
            }
        } else {
            System.err.println("The validated JSON is not an array of test cases.");
        }

        return testCases;
    }
}
