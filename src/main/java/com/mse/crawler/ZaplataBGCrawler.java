package com.mse.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mse.entities.JobListing;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Web crawler implementation tailored for the Response type / HTML structure of the zaplata.bg website
 */
@Getter
public class ZaplataBGCrawler extends WebCrawler {
    /**
     * Base URL for the global Zaplata.bg listing search. Ajax endpoint which returns JSON data including listing markup
     */
    static final String URL = "https://www.zaplata.bg/search_ajax?go=";

    /**
     * Constructs a ZaplataBGCrawler object with a given thread count and page count.
     * @param threadCount The number of threads to be used for parallel crawling.
     * @param pageCount The number of pages to be crawled.
     */
    ZaplataBGCrawler(int threadCount, int pageCount) {
        super(threadCount, pageCount);
    }

    /**
     * Crawls a specified page of the zaplata.bg website and retrieves its job listings.
     * @param page The page number to be crawled.
     * @return The JobListing objects representing the job listings found on the page.
     */
    @Override
    public ArrayList<JobListing> crawlPage (int page) {
        String responseString;
        ArrayList<JobListing> pageListing = new ArrayList<>();
        String pageURL = URL + "&" + PAGE_URL_PARAMETER + "=" + page;
        Connection connection = Jsoup.connect(pageURL).userAgent(USER_AGENT).timeout(TIMEOUT).ignoreContentType(true);

        // Fetches the raw JSON string output from the Ajax endpoint
        try {
            responseString = connection.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parses the JSON data into a traversable node tree using jackson
        JsonNode jsonNode;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonNode = objectMapper.readTree(responseString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Converts the HTML json property into Jsoup document instance
        String responseHTML = jsonNode.get("html").asText();
        Document pageContent = Jsoup.parse(responseHTML);

        Elements listingElements = pageContent.getElementsByTag("item");

        for (Element listingElement : listingElements) {
            Element listingLinkElement = listingElement.selectFirst(".title a");
            String jobTitle = listingLinkElement.text();
            String jobLink = listingLinkElement.attr("href");

            pageListing.add(new JobListing(jobLink, jobTitle));
        }

        this.listings = pageListing;
        return pageListing;
    }
}
