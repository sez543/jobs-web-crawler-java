package com.mse.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Single job listing retrieved from a website.
 */
@Getter
@Setter
public class JobListing {
    private String jobURL;
    private String jobName;

    /**
     * Constructs a JobListing object with the specified job URL and job name.
     * @param jobURL  The URL of the job listing.
     * @param jobName The name of the job.
     */
    public JobListing(String jobURL, String jobName) {
        this.jobURL = jobURL;
        this.jobName = jobName;
    }
}
