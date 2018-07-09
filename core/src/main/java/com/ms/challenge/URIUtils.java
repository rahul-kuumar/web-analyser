package com.ms.challenge;

import java.net.URI;
import java.net.URISyntaxException;

public class URIUtils {

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();


        return (domain != null && domain.startsWith("www.")) ? domain.substring(4) : domain;
    }
}
