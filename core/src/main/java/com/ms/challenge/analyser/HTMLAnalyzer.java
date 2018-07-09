package com.ms.challenge.analyser;

import com.ms.challenge.entity.HeaderCounts;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HTMLAnalyzer implements IHTMLAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(HTMLAnalyzer.class);
    private static final Map<String, String> htmlVersionMap = new HashMap<>();

    static {
        //Below map is created from https://www.w3.org/QA/2002/04/valid-dtd-list.html.
        //Additional entries may be needed in actual production code.
        htmlVersionMap.put("-//W3C//DTD HTML 4.01//EN", "HTML 4.01 Strict");
        htmlVersionMap.put("-//W3C//DTD HTML 4.01 Transitional//EN", "HTML 4.01 Transitional");
        htmlVersionMap.put("-//W3C//DTD HTML 4.01 Frameset//EN", "HTML 4.01 Frameset");

        htmlVersionMap.put("-//W3C//DTD XHTML 1.0 Strict//EN", "XHTML 1.0 Strict");
        htmlVersionMap.put("-//W3C//DTD XHTML 1.0 Transitional//EN", "XHTML 1.0 Transitional");
        htmlVersionMap.put("-//W3C//DTD XHTML 1.0 Frameset//EN", "XHTML 1.0 Frameset");

        htmlVersionMap.put("-//W3C//DTD XHTML 1.1//EN", "XHTML 1.1");
        htmlVersionMap.put("-//W3C//DTD XHTML Basic 1.1//EN", "XHTML Basic 1.1");
    }


    private final Document document;

    public HTMLAnalyzer(Document document) {
        this.document = document;
    }


    @Override
    public String getVersion() {
        logger.debug("Calling Get Version");
        List<Node> nodes = document.childNodes();
        String publicId = null;
        for (Node node : nodes) {
            if (node instanceof DocumentType) {
                DocumentType documentType = (DocumentType) node;
                publicId = documentType.attr("publicid");
                if ("".equals(publicId)) {
                    return "HTML5";
                }
            }
        }

        return htmlVersionMap.getOrDefault(publicId, "Unknown Version");
    }


    @Override
    public String getTitle() {
        return document.title();
    }

    @Override
    public HeaderCounts getHeadersCount() {
        return new HeaderCounts(
                getHeaderCount("h1"),
                getHeaderCount("h2"),
                getHeaderCount("h3"),
                getHeaderCount("h4"),
                getHeaderCount("h5"),
                getHeaderCount("h6")


        );
    }

    private int getHeaderCount(java.lang.String tag) {
        return document.getElementsByTag(tag).size();
    }


}
