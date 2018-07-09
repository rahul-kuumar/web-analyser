package com.ms.challenge.entity;

public final class ExternalLinkDetails {
    private final String url;
    private final boolean isReachable;
    private final String failureReason;


    public ExternalLinkDetails(String url, boolean isReachable, String failureReason) {
        this.url = url;
        this.isReachable = isReachable;
        this.failureReason = failureReason;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public String getUrl() {
        return url;
    }

    public String getFailureReason() {
        return failureReason;
    }

    @Override
    public String toString() {
        return "ExternalLinkDetails{" +
                ", url='" + url + '\'' +
                ", isReachable=" + isReachable +
                ", failureReason='" + failureReason + '\'' +
                '}';
    }
}
