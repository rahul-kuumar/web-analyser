package com.ms.challenge.analyser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.ms.challenge.analyser.TestUtils.getFile;

public class LoginFormAnalyzerTester {

    @Test
    public void hasLoginForm() throws IOException {
        File in = getFile("/htmltests/login.html");
        Document doc = Jsoup.parse(in, "UTF-8");


        LoginFormAnalyzer analyzer = new LoginFormAnalyzer(doc);
        Assert.assertTrue(analyzer.hasLoginForm());
    }
}

