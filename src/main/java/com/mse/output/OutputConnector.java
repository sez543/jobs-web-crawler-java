package com.mse.output;

import com.mse.entities.JobListing;

import java.util.ArrayList;

/**
 * Abstract connector class for saving job listings in persistent storage
 */
public abstract class OutputConnector {
    /**
     * Establishes all needed connections for the particular data output
     * @return A boolean success status of the performed connections
     */
    public abstract boolean connect();

    /**
     * Saves the passed in job listings in external storage
     * @param listings An ArrayList of job listing objects
     * @return A boolean success status of the save operation
     */
    public abstract boolean saveListings(ArrayList<JobListing> listings);

    /**
     * Closes all connections created during the object lifetime
     */
    public abstract void drop();
}
