package com.mdscem.apitestframework.fileprocessor.filereader;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

import com.mdscem.apitestframework.fileprocessor.fileinterpreter.FileInterpreter;
import com.mdscem.apitestframework.fileprocessor.filevalidator.JsonSchemaValidationWithJsonNode;

public class ApiTestMain {

    public static void main(String[] args) throws Exception {

        List<String> testCaseFiles= FileConfigLoader.loadTestCasesFiles();
        JsonNode jsonNode = FileConfigLoader.readFile(testCaseFiles);
        JsonNode validateNode = JsonSchemaValidationWithJsonNode.validateFile(jsonNode);
        FileInterpreter.interpret(validateNode).get(0);
    }
}
