package com.ms.challenge.entity;


public final class AnalysisOutput {

    private final String url;
    private final String htmlVersion;
    private final String title;
    private final HeaderCounts headers;
    private final LinkCounts linkCounts;
    private final boolean hasLogin;

    public AnalysisOutput(String url, String htmlVersion, String title, HeaderCounts headers, LinkCounts linkCounts, boolean hasLogin) {
        this.url = url;
        this.htmlVersion = htmlVersion;
        this.title = title;
        this.headers = headers;
        this.linkCounts = linkCounts;
        this.hasLogin = hasLogin;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlVersion() {
        return htmlVersion;
    }

    public String getTitle() {
        return title;
    }

    public HeaderCounts getHeaders() {
        return headers;
    }

    public LinkCounts getLinkCounts() {
        return linkCounts;
    }

    public boolean hasLogin() {
        return hasLogin;
    }
}
