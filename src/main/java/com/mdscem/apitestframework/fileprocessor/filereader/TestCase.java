package com.mdscem.apitestframework.fileprocessor.filereader;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class TestCase {
    private String id;
    private String url;
    private String method;
    private JsonNode payload;
    private String expectedResponseCode;
    private String testName;
    private JsonNode expectedResponseBody;  // Changed to JsonNode to handle arrays or objects
    private String requiresAuthentication;
    private String authToken;
    private String priority;
    private Map<String, String> saveResponse;

    // Getters and Setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public void setPayload(JsonNode payload) {
        this.payload = payload;
    }

    public String getExpectedResponseCode() {
        return expectedResponseCode;
    }

    public void setExpectedResponseCode(String expectedResponseCode) {
        this.expectedResponseCode = expectedResponseCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public JsonNode getExpectedResponseBody() {
        return expectedResponseBody;
    }

    public void setExpectedResponseBody(JsonNode expectedResponseBody) {
        this.expectedResponseBody = expectedResponseBody;
    }

    public String getRequiresAuthentication() {
        return requiresAuthentication;
    }

    public void setRequiresAuthentication(String requiresAuthentication) {
        this.requiresAuthentication = requiresAuthentication;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Map<String, String> getSaveResponse() {
        return saveResponse;
    }

    public void setSaveResponse(Map<String, String> saveResponse) {
        this.saveResponse = saveResponse;
    }
}
