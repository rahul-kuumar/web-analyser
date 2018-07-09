package com.ms.challenge;

import com.ms.challenge.entity.AnalysisOutput;
import com.ms.challenge.entity.ExternalLinkDetails;

import java.util.List;

public interface IWebAnalysisServiceProxy {
    AnalysisOutput analyzeWebPage(String url);

    List<ExternalLinkDetails> analyzeExternalLinks(String url);
}
