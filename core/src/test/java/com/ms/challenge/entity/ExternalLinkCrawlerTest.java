package com.ms.challenge.entity;

import com.ms.challenge.analyser.ExternalLinkCrawler;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.doThrow;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public final class ExternalLinkCrawlerTest {

    private void mock_success() throws IOException {
        Connection connection = Mockito.mock(Connection.class);
        Connection.Response response = Mockito.mock(Connection.Response.class);
        PowerMockito.mockStatic(Jsoup.class);

        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
        PowerMockito.when(connection.followRedirects(true)).thenReturn(connection);
        PowerMockito.when(connection.timeout(5000)).thenReturn(connection);

        PowerMockito.when(connection.execute()).thenReturn(response);
    }

    private void mock_failure() throws IOException {
        Connection connection = Mockito.mock(Connection.class);
        PowerMockito.mockStatic(Jsoup.class);

        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);
        PowerMockito.when(connection.followRedirects(true)).thenReturn(connection);
        PowerMockito.when(connection.timeout(5000)).thenReturn(connection);

        doThrow(new RuntimeException("failure_reason")).when(connection).execute();
    }

    @Test
    public void should_be_able_to_reach_url() throws IOException {

        mock_success();
        List<String> urlList = new ArrayList<>(1);
        urlList.add("some_url");


        ExternalLinkCrawler externalLinkCrawler = new ExternalLinkCrawler(urlList);
        List<ExternalLinkDetails> externalLinkDetailsList = externalLinkCrawler.crawl();
        Assert.assertEquals(1, externalLinkDetailsList.size());
        Assert.assertTrue(externalLinkDetailsList.get(0).isReachable());
    }

    @Test
    public void should_not_be_able_to_reach_url_with_incorrect_link() throws IOException {
        mock_failure();
        List<String> urlList = new ArrayList<>(1);
        urlList.add("some_url");
        ExternalLinkCrawler externalLinkCrawler = new ExternalLinkCrawler(urlList);
        List<ExternalLinkDetails> externalLinkDetailsList = externalLinkCrawler.crawl();
        Assert.assertEquals(1, externalLinkDetailsList.size());
        Assert.assertFalse(externalLinkDetailsList.get(0).isReachable());
        Assert.assertEquals("failure_reason", externalLinkDetailsList.get(0).getFailureReason());

    }


    @Test
    public void should_be_able_to_handle_multiple_urls() throws IOException {
        mock_success();
        List<String> urlList = new ArrayList<>(4);
        urlList.add("url1");
        urlList.add("url2");
        urlList.add("url3");
        urlList.add("url4");
        ExternalLinkCrawler externalLinkCrawler = new ExternalLinkCrawler(urlList);
        List<ExternalLinkDetails> externalLinkDetailsList = externalLinkCrawler.crawl();
        Assert.assertEquals(4, externalLinkDetailsList.size());


        Assert.assertTrue(externalLinkDetailsList.get(0).isReachable());
        Assert.assertNull(externalLinkDetailsList.get(0).getFailureReason());

        Assert.assertTrue(externalLinkDetailsList.get(1).isReachable());
        Assert.assertNull(externalLinkDetailsList.get(1).getFailureReason());

        Assert.assertTrue(externalLinkDetailsList.get(2).isReachable());
        Assert.assertNull(externalLinkDetailsList.get(1).getFailureReason());

        Assert.assertTrue(externalLinkDetailsList.get(3).isReachable());
        Assert.assertNull(externalLinkDetailsList.get(3).getFailureReason());


    }
}