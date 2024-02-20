package com.mse.crawler;

import com.mse.entities.JobListing;
import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Web crawler implementation tailored for the HTML structure of the jobs.bg website
 */
@Getter
public class JobsBGCrawler extends WebCrawler {
    /**
     * Base URL for the global Jobs.bg listing search. Ajax endpoint which returns only listing markup
     */
    static final String URL = "https://www.jobs.bg/front_job_search.php?ajax=1";

    /**
     * Constructs a JobsBGCrawler object with a given thread count and page count.
     * @param threadCount The number of threads to be used for parallel crawling.
     * @param pageCount The number of pages to be crawled.
     */
    JobsBGCrawler(int threadCount, int pageCount) {
        super(threadCount, pageCount);
    }

    /**
     * Crawls a specified page of the jobs.bg website and retrieves its job listings.
     * @param page The page number to be crawled.
     * @return The JobListing objects representing the job listings found on the page.
     */
    @Override
    public ArrayList<JobListing> crawlPage (int page) {
        ArrayList<JobListing> pageListing = new ArrayList<>();
        Document pageContent = getPageContent(URL, page);
        Elements listingElements = pageContent.getElementsByClass("scroll-item");

        for (Element listingElement : listingElements) {
            Element listingLinkElement = listingElement.selectFirst(".left .black-link-b");
            String jobTitle = listingLinkElement.attr("title");
            String jobLink = listingLinkElement.attr("href");

            pageListing.add(new JobListing(jobLink, jobTitle));
        }

        this.listings = pageListing;
        return pageListing;
    }
}
