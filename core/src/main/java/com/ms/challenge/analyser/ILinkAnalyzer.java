package com.ms.challenge.analyser;

import com.ms.challenge.entity.LinkCounts;
import org.jsoup.nodes.Document;

import java.util.List;

public interface ILinkAnalyzer {
    void analyze(String url, Document document);

    LinkCounts getLinkCounts();

    List<String> getExternalUrlList();

}
