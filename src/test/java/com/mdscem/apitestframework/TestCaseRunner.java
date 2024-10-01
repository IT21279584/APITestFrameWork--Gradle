package com.mdscem.apitestframework;

import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestCaseRunner {
    private TestCase testCase;
    private CoreFramework coreFramework;

    public TestCaseRunner(TestCase testCase, CoreFramework coreFramework) {
        this.testCase = testCase;
        this.coreFramework = coreFramework;
    }

    @Test
    @DisplayName("{testCase.getTestName()}") // Use the test case name for a descriptive display name
    public void runTestCase() {
        // Execute the test case using coreFramework
        coreFramework.executeTestCase(testCase);
    }
}
