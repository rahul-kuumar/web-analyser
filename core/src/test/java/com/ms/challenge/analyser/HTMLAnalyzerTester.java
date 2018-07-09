package com.ms.challenge.analyser;

import com.ms.challenge.entity.HeaderCounts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class HTMLAnalyzerTester {


    @Test
    public void should_test_version_title_and_header_counts() throws IOException {
        File in = TestUtils.getFile("/htmltests/smh-biz-article-1.html");
        Document doc = Jsoup.parse(in, "UTF-8");


        HTMLAnalyzer htmlAnalyzer = new HTMLAnalyzer(doc);
        String version = htmlAnalyzer.getVersion();
        String title = htmlAnalyzer.getTitle();
        HeaderCounts counts = htmlAnalyzer.getHeadersCount();

        Assert.assertEquals("XHTML 1.0 Transitional", version);
        Assert.assertEquals("The board’s next fear: the female quota", title);
        Assert.assertEquals(1, counts.getH1());
        Assert.assertEquals(10, counts.getH2());
        Assert.assertEquals(18, counts.getH3());
        Assert.assertEquals(8, counts.getH4());
        Assert.assertEquals(14, counts.getH5());
        Assert.assertEquals(0, counts.getH6());
    }


    @Test
    public void should_test_version_for_html5() throws IOException {
        File in = TestUtils.getFile("/htmltests/login.html");
        Document doc = Jsoup.parse(in, "UTF-8");


        HTMLAnalyzer htmlAnalyzer = new HTMLAnalyzer(doc);
        String version = htmlAnalyzer.getVersion();
        Assert.assertEquals("HTML5", version);
    }


}