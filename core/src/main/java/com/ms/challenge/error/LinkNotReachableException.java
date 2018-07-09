package com.ms.challenge.error;

@SuppressWarnings("serial")
public class LinkNotReachableException extends Exception {
    private final String link;

    public LinkNotReachableException(String rootUrl, String msg, Throwable cause) {
        super(msg, cause);
        this.link = rootUrl;
    }

    public String getLink() {
        return link;
    }
}
