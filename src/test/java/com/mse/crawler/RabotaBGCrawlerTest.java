package com.mse.crawler;

import com.mse.entities.ApplicationConfig;
import com.mse.entities.JobListing;
import com.mse.enums.WebsiteID;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RabotaBGCrawlerTest {
    @Test
    public void testCrawl () {
        ApplicationConfig config = new ApplicationConfig(1, 1, WebsiteID.RABOTA);
        WebCrawler crawler = new CrawlerFactory(config).getCrawler();
        assertDoesNotThrow(crawler::crawl);
        ArrayList<JobListing> listings = crawler.getListings();
        assertNotNull(listings);
        assertFalse(listings.isEmpty());
    }
}
