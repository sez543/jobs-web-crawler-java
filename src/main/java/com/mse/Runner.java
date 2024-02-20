package com.mse;

import com.mse.input.CommandLineInterface;
import com.mse.crawler.CrawlerFactory;
import com.mse.crawler.WebCrawler;
import com.mse.entities.ApplicationConfig;
import com.mse.entities.JobListing;
import com.mse.enums.ConnectorType;
import com.mse.output.OutputConnector;
import com.mse.output.OutputManager;

import java.util.ArrayList;

public class Runner {
    public static void main (String [] args) {
        ApplicationConfig config = new CommandLineInterface().getConfig();
        WebCrawler crawler = new CrawlerFactory(config).getCrawler();
        ArrayList<JobListing> jobListings = crawler.crawl();
        OutputConnector jsonSerializer = OutputManager.getConnectorInstance(ConnectorType.JSON);
        jsonSerializer.connect();
        jsonSerializer.saveListings(jobListings);
        jsonSerializer.drop();
    }
}
