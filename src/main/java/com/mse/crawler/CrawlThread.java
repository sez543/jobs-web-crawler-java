package com.mse.crawler;

import com.mse.entities.JobListing;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Crawl task, which may be executed in a separate thread, responsible for crawling a single page and retrieving job listings.
 */
public class CrawlThread implements Callable<ArrayList<JobListing>> {
    private final WebCrawler crawler;
    private final int page;

    /**
     * Constructs a CrawlThread object with a given web crawler and page number.
     * @param crawler The web crawler instance responsible for crawling the page.
     * @param page The page number to be crawled.
     */
    CrawlThread(WebCrawler crawler, int page) {
        this.crawler = crawler;
        this.page = page;
    }

    /**
     * Executes the defined pageCrawl implementation for the specified page as per the used Crawler subclass.
     * @return An ArrayList of JobListing objects representing the jobs found on the page.
     * @throws Exception If an error occurs during the crawling process.
     */
    @Override
    public ArrayList<JobListing> call() throws Exception {
        return crawler.crawlPage(page);
    }
}
