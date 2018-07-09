package com.ms.challenge;

import com.ms.challenge.analyser.TestUtils;
import com.ms.challenge.entity.AnalysisOutput;
import com.ms.challenge.entity.ExternalLinkDetails;
import com.ms.challenge.entity.HeaderCounts;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public final class WebAnalysisServiceProxyTester {

    private void mock(Document doc) throws IOException {
        Connection connection = Mockito.mock(Connection.class);
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
        PowerMockito.when(connection.get()).thenReturn(doc);
    }


    @Test
    public void should_test_version_title_and_header_counts() throws IOException {
        File in = TestUtils.getFile("/htmltests/nyt-article-1.html");
        Document doc = Jsoup.parse(in, "UTF-8");

        mock(doc);

        IWebAnalysisServiceProxy proxy = new WebAnalysisServiceProxy();
        AnalysisOutput analysisOutput = proxy.analyzeWebPage("http://www.nytimes.com/");
        HeaderCounts counts = analysisOutput.getHeaders();

        Assert.assertEquals("HTML 4.01 Transitional", analysisOutput.getHtmlVersion());
        Assert.assertEquals("BP to Replace Hayward as Chief Executive - NYTimes.com", analysisOutput.getTitle());

        Assert.assertEquals(1, counts.getH1());
        Assert.assertEquals(2, counts.getH2());
        Assert.assertEquals(5, counts.getH3());
        Assert.assertEquals(3, counts.getH4());
        Assert.assertEquals(1, counts.getH5());
        Assert.assertEquals(28, counts.getH6());

        Assert.assertEquals(35, analysisOutput.getLinkCounts().getExternal());
        Assert.assertEquals(143, analysisOutput.getLinkCounts().getInternal());

        Assert.assertEquals("http://www.nytimes.com/", analysisOutput.getUrl());
    }


    @Test
    public void analyzeExternalLinks() throws IOException {
        File in = TestUtils.getFile("/htmltests/nyt-article-1.html");
        Document doc = Jsoup.parse(in, "UTF-8");

        mock(doc);

        IWebAnalysisServiceProxy proxy = new WebAnalysisServiceProxy();
        List<ExternalLinkDetails> linkDetails = proxy.analyzeExternalLinks("http://www.nytimes.com/");
        Assert.assertEquals(35, linkDetails.size());

    }
}