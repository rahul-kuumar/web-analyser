package com.ms.challenge.analyser;

import com.ms.challenge.entity.HeaderCounts;

public interface IHTMLAnalyzer {
    String getVersion();

    String getTitle();

    HeaderCounts getHeadersCount();

}
