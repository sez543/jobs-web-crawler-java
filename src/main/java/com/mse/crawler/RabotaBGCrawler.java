package com.mse.crawler;

import com.mse.entities.JobListing;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Web crawler implementation tailored for the HTML structure of the rabota.bg website
 */
@Getter
public class RabotaBGCrawler extends WebCrawler {
    /**
     * Base URL for the global Rabota.bg listing search
     */
    static final String URL = "https://www.rabota.bg/search.php?kw=&country%5BBGR%5D=BGR";

    /**
     * Constructs a RabotaBGCrawler object with a given thread count and page count.
     * @param threadCount The number of threads to be used for parallel crawling.
     * @param pageCount The number of pages to be crawled.
     */
    RabotaBGCrawler(int threadCount, int pageCount) {
        super(threadCount, pageCount);
    }

    /**
     * Crawls a specified page of the rabota.bg website and retrieves its job listings.
     * @param page The page number to be crawled.
     * @return The JobListing objects representing the job listings found on the page.
     */
    @Override
    public ArrayList<JobListing> crawlPage (int page) {
        ArrayList<JobListing> pageListing = new ArrayList<>();
        Document pageContent = getPageContent(URL, page);
        Elements listingElements = pageContent.getElementsByClass("r");

        for (Element listingElement : listingElements) {
            Element listingLinkElement = listingElement.selectFirst(".searchLink");
            Element listingTitleElement = listingElement.selectFirst("span");
            String jobTitle = listingTitleElement.text();
            String jobLink = listingLinkElement.attr("href");

            pageListing.add(new JobListing(jobLink, jobTitle));
        }

        this.listings = pageListing;
        return pageListing;
    }
}
