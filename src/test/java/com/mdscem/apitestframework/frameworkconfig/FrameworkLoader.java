package com.mdscem.apitestframework.frameworkconfig;

import com.mdscem.apitestframework.frameworkImplementations.KarateCoreFramework;
import com.mdscem.apitestframework.frameworkImplementations.RestAssuredCoreFramework;
import com.mdscem.apitestframework.requestprocessor.CoreFramework;
import com.mdscem.apitestframework.requestprocessor.FrameworkAdapter;

import java.io.IOException;

public class FrameworkLoader {

    public CoreFramework loadFrameworkFromConfig() throws IOException {
        String frameworkType = FrameworkAdapter.loadFrameworkTypeFromConfig();
        System.out.println("Framework loaded from config: " + frameworkType);

        switch (frameworkType.toLowerCase()) {
            case "restassured":
                return new RestAssuredCoreFramework();
            case "karate":
                return new KarateCoreFramework();
            default:
                throw new IllegalArgumentException("Unsupported framework type: " + frameworkType);
        }
    }
}
