package com.ms.challenge.analyser;

import com.ms.challenge.entity.ExternalLinkDetails;
import com.ms.challenge.error.LinkNotReachableException;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/*
The thread runs ExternalLinkDetailsTask, which checks if the link is accessible with timeout set to 5 sec.
 */
final class ExternalLinkDetailsTask implements Callable<ExternalLinkDetails> {
    private final static Logger logger = LoggerFactory.getLogger(ExternalLinkDetailsTask.class);
    private final String rootUrl;

    public ExternalLinkDetailsTask(String aUrl) {
        rootUrl = aUrl;
    }


    @Override
    public ExternalLinkDetails call() throws LinkNotReachableException {
        try {
            Jsoup.connect(rootUrl)
                    .followRedirects(true)
                    .timeout(5000)
                    .execute();
        } catch (Exception e) {
            logger.error("Unable to reach url, {}", e.getMessage());
            throw new LinkNotReachableException(rootUrl, e.getMessage(), e.getCause());

        }
        return new ExternalLinkDetails(rootUrl, true, null);
    }


}
