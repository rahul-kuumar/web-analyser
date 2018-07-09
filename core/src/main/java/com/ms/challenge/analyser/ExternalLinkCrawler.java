package com.ms.challenge.analyser;

import com.ms.challenge.entity.ExternalLinkDetails;
import com.ms.challenge.error.LinkNotReachableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
 * This Class checks if the external link is accessible using multiple threads.
 * The thread runs ExternalLinkDetailsTask, which checks if the link is accessible with timeout set to 5 sec.
 * Since this operation is IO bound, Executors.newCachedThreadPool() has been used to create thread pool.
 * Further, for performance reason ExecutorCompletionService is used to submit request for future as
 * completionService.take() is non-blocking and picks up result as soon as it is available in the underlying queue.
 *
 *
 *
 */
public final class ExternalLinkCrawler {
    private final static Logger logger = LoggerFactory.getLogger(ExternalLinkCrawler.class);
    private final List<String> urlList;

    public ExternalLinkCrawler(List<String> urlList) {
        this.urlList = urlList;
    }

    public List<ExternalLinkDetails> crawl() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<ExternalLinkDetails> completionService = new ExecutorCompletionService<>(
                executorService);
        List<Future<ExternalLinkDetails>> futureList = new ArrayList<>();
        for (String url : urlList) {
            ExternalLinkDetailsTask task = new ExternalLinkDetailsTask(url);
            Future<ExternalLinkDetails> result = completionService.submit(task);
            futureList.add(result);
        }

        List<ExternalLinkDetails> externalLinkDetailsList = new ArrayList<>(futureList.size());
        for (int i = 0; i < futureList.size(); i++) {
            try {
                Future<ExternalLinkDetails> futureResult = completionService.take();
                ExternalLinkDetails externalLinkDetails = futureResult.get();
                externalLinkDetailsList.add(externalLinkDetails);

            } catch (InterruptedException e) {
                //Set the interrupted flag and continue with next iteration.
                Thread.currentThread().interrupt();
                logger.error("Thread {} has been interrupted {}", Thread.currentThread().getId(), e.getMessage());

            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof LinkNotReachableException) {
                    LinkNotReachableException lnrEx = (LinkNotReachableException) cause;
                    externalLinkDetailsList.add(new ExternalLinkDetails(lnrEx.getLink(), false, cause.getMessage()));
                    logger.error("Error -{} in accessing url {}", e.getMessage(), lnrEx.getLink());
                } else {
                    logger.error("Execution exception in {}, {}", Thread.currentThread().getId(), e.getMessage());

                }
            }
        }
        executorService.shutdown();
        awaitTermination(executorService);

        return externalLinkDetailsList;
    }

    private void awaitTermination(ExecutorService executorService) {
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread {} has been interrupted {}", Thread.currentThread().getId(), e.getMessage());
        }
    }
}
