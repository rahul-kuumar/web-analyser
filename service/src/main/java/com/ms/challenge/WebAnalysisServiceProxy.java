package com.ms.challenge;

import com.ms.challenge.analyser.*;
import com.ms.challenge.entity.AnalysisOutput;
import com.ms.challenge.entity.ExternalLinkDetails;
import com.ms.challenge.entity.HeaderCounts;
import com.ms.challenge.entity.LinkCounts;
import com.ms.challenge.error.BadRequestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WebAnalysisServiceProxy implements IWebAnalysisServiceProxy {
    private final static Logger logger = LoggerFactory.getLogger(WebAnalysisServiceProxy.class);

    @Override
    public AnalysisOutput analyzeWebPage(String url) {
        logger.info("WebAnalysisServiceProxy analyzeWebPage called with url{}.", url);
        Document document = createDocument(url);

        IHTMLAnalyzer analyzer = new HTMLAnalyzer(document);
        String version = analyzer.getVersion();
        String title = analyzer.getTitle();
        HeaderCounts headerCounts = analyzer.getHeadersCount();

        ILinkAnalyzer linkAnalyzer = new LinkAnalyzer();
        linkAnalyzer.analyze(url, document);
        LinkCounts linkCounts = linkAnalyzer.getLinkCounts();


        ILoginFormAnalyzer loginFormAnalyzer = new LoginFormAnalyzer(document);
        boolean hasLogin = loginFormAnalyzer.hasLoginForm();


        return new AnalysisOutput(url, version, title, headerCounts, linkCounts, hasLogin);
    }


    @Override
    public List<ExternalLinkDetails> analyzeExternalLinks(String url) {
        logger.info("WebAnalysisServiceProxy analyzeWebPage called with url{}.", url);

        Document document = createDocument(url);
        LinkAnalyzer linkAnalyzer = new LinkAnalyzer();
        linkAnalyzer.analyze(url, document);
        List<String> externalUrlList = linkAnalyzer.getExternalUrlList();

        return new ExternalLinkCrawler(externalUrlList).crawl();
    }


    private Document createDocument(String url) {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error("Exception connecting to url, {}" + e.getMessage());
            throw new BadRequestException(e);
        }
        return document;
    }

}
