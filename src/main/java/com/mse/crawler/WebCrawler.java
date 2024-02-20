package com.mse.crawler;
import com.mse.entities.JobListing;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * An abstract web crawler for job listing websites capable of parallel execution
 */
@Getter
public abstract class WebCrawler {
    int threadCount;
    int pageCount;
    static final String PAGE_URL_PARAMETER = "page";
    /**
     * Firefox (Windows) user agent to replace the headless one placed by JSOUP
     */
    static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.3";
    /**
     * JSOUP request timeout to avoid rate limits
     */
    static final int TIMEOUT = 2000;
    static final Logger logger = LogManager.getLogger(WebCrawler.class);
    ArrayList<JobListing> listings;

    /**
     * Constructs a WebCrawler object with the specified thread count and page count.
     * @param threadCount The number of threads to be used for crawling.
     * @param pageCount The total number of pages to be crawled.
     */
    WebCrawler (int threadCount, int pageCount) {
        this.threadCount = threadCount;
        this.pageCount = pageCount;
    }

    /**
     * Establishes a connection to the passed URL and returns the Document object for its HTML content
     * @param url  The URL of the web page.
     * @param page The page number to be retrieved.
     * @return The document representing the content of the web page.
     */
    static Document getPageContent (String url, int page) {
        Document pageContent;
        String pageURL = url + "&" + PAGE_URL_PARAMETER + "=" + page;
        Connection connection = Jsoup.connect(pageURL).userAgent(USER_AGENT).timeout(TIMEOUT);

        try {
            pageContent = connection.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pageContent;
    }

    /**
     * Crawls a specified page and retrieves its job listings.
     * @param page The page number to be crawled.
     * @return The JobListing objects representing the listings found on the page.
     */
    public abstract ArrayList<JobListing> crawlPage(int page);

    /**
     * Crawls all the pages of the website within the given limit and retrieves its job listings.
     * @return The JobListing objects representing the listings found on all pages.
     */
    public ArrayList<JobListing> crawl() {
        // Thread pool implementation for parallel parsing of individual pages
        ExecutorService executorService = Executors.newFixedThreadPool(this.threadCount);
        List<Future<ArrayList<JobListing>>> futures = new ArrayList<>();

        for (int page = 1; page <= this.pageCount; page++) {
            Future<ArrayList<JobListing>> future = executorService.submit(new CrawlThread(this, page));
            futures.add(future);
        }

        ArrayList<JobListing> jobListings = new ArrayList<>();

        // Job listing output collection from each crawler thread
        for (Future<ArrayList<JobListing>> future : futures) {
            try {
                jobListings.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error while getting job listings from page", e);
            }
        }

        executorService.shutdown();

        return jobListings;
    };
}
