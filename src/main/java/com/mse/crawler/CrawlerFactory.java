package com.mse.crawler;

import com.mse.entities.ApplicationConfig;
import com.mse.enums.WebsiteID;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Factory class responsible for creating instances of specific web crawler implementations
 * based on a provided application configuration.
 */
@Getter
public class CrawlerFactory {
    private WebCrawler crawler;
    private final ApplicationConfig config;
    private static final Logger logger = LogManager.getLogger(WebCrawler.class);

    /**
     * Constructs a CrawlerFactory object based on the given application configuration.
     * @param config The application configuration specifying the website to crawl, thread count, and page count.
     */
    public CrawlerFactory(ApplicationConfig config) {
        this.config = config;

        WebsiteID crawledWebsite = config.getWebsite();
        int threadCount = config.getThreadCount();
        int pageCount = config.getPageCount();
        switch (crawledWebsite) {
            case JOBS:
                this.crawler = new JobsBGCrawler(threadCount, pageCount);
                break;
            case RABOTA:
                this.crawler = new RabotaBGCrawler(threadCount, pageCount);
                break;
            case ZAPLATA:
                this.crawler = new ZaplataBGCrawler(threadCount, pageCount);
                break;
            default:
                logger.error("Unsupported website: " + crawledWebsite);
        }
    }
}
