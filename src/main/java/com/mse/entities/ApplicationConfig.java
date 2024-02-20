package com.mse.entities;

import com.mse.enums.WebsiteID;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration settings for the web crawler application.
 */
@Getter
@Setter
public class ApplicationConfig {
    private int threadCount;
    private int pageCount;
    private WebsiteID website;

    /**
     * Constructs an ApplicationConfig object with the specified thread count, page count, and website.
     * @param threadCount The number of threads to be used for crawling.
     * @param pageCount The total number of pages to be crawled.
     * @param website The website enum id to be crawled.
     */
    public ApplicationConfig(int threadCount, int pageCount, WebsiteID website) {
        this.threadCount = threadCount;
        this.pageCount = pageCount;
        this.website = website;
    }
}
