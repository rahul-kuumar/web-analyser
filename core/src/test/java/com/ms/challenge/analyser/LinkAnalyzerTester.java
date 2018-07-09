package com.ms.challenge.analyser;

import com.ms.challenge.entity.LinkCounts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LinkAnalyzerTester {

    @Test
    public void testLinkCount() {
        String html = "<!DOCTYPE html>";
        html += "<html><head> </head><body>";
        html += "<a href='http://externalsite.com/'>externalsite</a>";
        html += "<a href='http://localhost:8080/abc/cde/index.html'>jsoup</a>";
        html += "<a href='/it/is/internal/header.html'>jsoup</a>";
        html += "</body></html>";


        Document doc = Jsoup.parse(html, "http://localhost:8080");
        LinkAnalyzer analyzer = new LinkAnalyzer();
        analyzer.analyze("http://localhost:8080", doc);
        LinkCounts linkCounts = analyzer.getLinkCounts();
        assertEquals(2, linkCounts.getInternal());
        assertEquals(1, linkCounts.getExternal());
    }

    @Test
    public void testCollectExternalLinkDetails() {
        String html = "<!DOCTYPE html>";
        html += "<html><head> </head><body>";
        html += "<a href='http://externalsite.com/'>externalsite</a>";
        html += "<a href='http://externalsite1.com/'>externalsite1</a>";
        html += "<a href='http://localhost:8080/abc/cde/index.html'>jsoup</a>";
        html += "<a href='/it/is/internal/header.html'>jsoup</a>";
        html += "</body></html>";


        Document doc = Jsoup.parse(html, "http://localhost:8080");
        LinkAnalyzer analyzer = new LinkAnalyzer();
        analyzer.analyze("http://localhost:8080", doc);
        List<String> externalLinkDetails = analyzer.getExternalUrlList();
        assertEquals(2, externalLinkDetails.size());
    }

}