package com.mse.entities;

import com.mse.enums.WebsiteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationConfigTest {
    private ApplicationConfig config;

    @BeforeEach
    public void setup() {
        int threadCount = 4;
        int pageCount = 10;
        WebsiteID website = WebsiteID.JOBS;
        config = new ApplicationConfig(threadCount, pageCount, website);
    }

    @Test
    public void testConstructor() {
        assertEquals(4, config.getThreadCount());
        assertEquals(10, config.getPageCount());
        assertEquals(WebsiteID.JOBS, config.getWebsite());
    }

    @Test
    public void testGettersAndSetters() {
        int newThreadCount = 8;
        int newPageCount = 15;
        WebsiteID newWebsite = WebsiteID.JOBS;

        config.setThreadCount(newThreadCount);
        config.setPageCount(newPageCount);
        config.setWebsite(newWebsite);

        assertEquals(newThreadCount, config.getThreadCount());
        assertEquals(newPageCount, config.getPageCount());
        assertEquals(newWebsite, config.getWebsite());
    }
}
