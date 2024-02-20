package com.mse.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobListingTest {
    private JobListing jobListing;

    @BeforeEach
    public void setup() {
        String jobURL = "https://example.com/job";
        String jobName = "Software Engineer";
        jobListing = new JobListing(jobURL, jobName);
    }

    @Test
    public void testJobListingConstructor() {
        assertEquals("https://example.com/job", jobListing.getJobURL());
        assertEquals("Software Engineer", jobListing.getJobName());
    }

    @Test
    public void testGetterAndSetter() {
        String newJobURL = "https://example.com/newjob";
        String newJobName = "Data Scientist";

        jobListing.setJobURL(newJobURL);
        jobListing.setJobName(newJobName);

        assertEquals("https://example.com/newjob", jobListing.getJobURL());
        assertEquals("Data Scientist", jobListing.getJobName());
    }
}
