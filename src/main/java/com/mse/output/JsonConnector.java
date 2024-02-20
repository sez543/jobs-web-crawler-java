package com.mse.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mse.entities.JobListing;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

/**
 * JSON file output connector
 */
@Getter
@Setter
public class JsonConnector extends OutputConnector {
    /**
     * Directory path for the saved files (Relative to /src folder)
     */
    private final String FILE_DIRECTORY = "./json/";
    private ObjectMapper mapper;
    private static final Logger logger = LogManager.getLogger(JsonConnector.class);

    /**
     * Creates the jackson object mapper instance and the destination directory folder, if those do not already exist
     * @return The status of the performed folder manipulation
     */
    @Override
    public boolean connect () {
        this.mapper = new ObjectMapper();

        File outputDirectory = new File(FILE_DIRECTORY);
        if (!outputDirectory.exists() && !outputDirectory.mkdirs()) {
            logger.error("Unable to create output directory!");
            return false;
        }

        return true;
    }

    /**
     * Use the Jackson API to save the passed in the ArrayList of job listing data to a JSON file
     * @param listings An ArrayList of job listing objects
     * @return A boolean success status of the save operation
     */
    @Override
    public boolean saveListings (ArrayList<JobListing> listings) {
        long unixTime = Instant.now().getEpochSecond();
        File outputFile = new File(FILE_DIRECTORY + "job_listings-" + unixTime + ".json");
        try {
            this.mapper.writeValue(outputFile, listings);
        } catch (IOException e) {
            logger.error("Unable to save output to JSON file!", e);
            return false;
        }

        return true;
    }

    /**
     * No connections to close for JSON output
     */
    @Override
    public void drop() {}
}
