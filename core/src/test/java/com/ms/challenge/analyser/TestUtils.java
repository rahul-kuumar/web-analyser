package com.ms.challenge.analyser;

import java.io.File;
import java.net.URISyntaxException;

public class TestUtils {
    public static File getFile(String resourceName) {
        try {
            return new File(TestUtils.class.getResource(resourceName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }


}
