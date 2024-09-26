package com.mdscem.apitestframework;

import com.mdscem.apitestframework.fileprocessor.filereader.TestCase;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import org.testng.annotations.Test;

public class TestCaseRunner {
    private TestCase testCase;
    private CoreFramework coreFramework;

    public TestCaseRunner(TestCase testCase, CoreFramework coreFramework) {
        this.testCase = testCase;
        this.coreFramework = coreFramework;
    }

    @Test
    public void runTestCase() {
        coreFramework.executeTestCase(testCase);
    }
}