package com.ms.challenge.analyser;

import com.ms.challenge.entity.LinkCounts;
import com.ms.challenge.error.BadRequestException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.ms.challenge.URIUtils.getDomainName;

public class LinkAnalyzer implements ILinkAnalyzer {
    private final static Logger logger = LoggerFactory.getLogger(LinkAnalyzer.class);

    private LinkCounts linkCounts;
    private final List<String> externalUrlList = new ArrayList<>();


    @Override
    public void analyze(String url, Document document) {
        String rootDomain;

        try {
            rootDomain = getDomainName(url);
        } catch (URISyntaxException e) {
            throw new BadRequestException("URL is invalid", e.getCause());
        }

        Elements links = document.select("a[href]");
        logger.debug("Number of total links in the page are {}", links.size());

        int internal = 0, external = 0;

        for (Element link : links) {
            String href = link.attr("abs:href");
            try {
                URI linkUrl = new URI(href);

                String protocol = linkUrl.getScheme();
                if ("http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol)) {
                    boolean isSameDomain = rootDomain.equalsIgnoreCase(getDomainName(href));

                    if (isSameDomain) {
                        internal++;
                    } else {
                        external++;
                        externalUrlList.add(href);
                    }
                }
            } catch (URISyntaxException e) {
                logger.warn("The link {} has invalid uri syntax.", link);
            }
        }

        linkCounts = new LinkCounts(internal, external);
    }


    @Override
    public LinkCounts getLinkCounts() {
        return linkCounts;
    }

    @Override
    public List<String> getExternalUrlList() {
        return externalUrlList;
    }


}
