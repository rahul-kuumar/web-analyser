package com.ms.challenge;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

import static com.ms.challenge.URIUtils.getDomainName;

public class URIUtilsTester {

    @Test
    public void should_test_get_domain_name() throws URISyntaxException {

        Assert.assertEquals("xyz.com", getDomainName("http://www.xyz.com"));
        Assert.assertEquals("google.com", getDomainName("https://www.google.com"));
        Assert.assertEquals("google.co.in", getDomainName("http://www.google.co.in"));
        Assert.assertEquals("google.co.in", getDomainName("https://www.google.co.in"));
    }

    @Test
    public void should_return_null_with_http() throws URISyntaxException {
        Assert.assertNull(getDomainName("www.google.com"));
    }
}