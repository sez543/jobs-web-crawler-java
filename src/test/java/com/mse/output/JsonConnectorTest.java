package com.mse.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mse.entities.JobListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JsonConnectorTest {
    private JsonConnector jsonConnector;

    @Mock
    private ObjectMapper mockMapper;

    @BeforeEach
    public void setup() {
        jsonConnector = new JsonConnector();
        jsonConnector.setMapper(mockMapper);
    }

    @Test
    public void testConnection() {
        assertTrue(jsonConnector.connect());
    }

    @Test
    public void testSaveSuccess() throws IOException {
        ArrayList<JobListing> listings = new ArrayList<>();
        listings.add(new JobListing("https://example.com/job1", "Job 1"));
        listings.add(new JobListing("https://example.com/job2", "Job 2"));
        doNothing().when(mockMapper).writeValue(any(File.class), any(ArrayList.class));

        boolean result = jsonConnector.saveListings(listings);

        assertTrue(result);
        verify(mockMapper, times(1)).writeValue(any(File.class), any(ArrayList.class));
    }

    @Test
    public void testSaveFailure() throws IOException {
        ArrayList<JobListing> listings = new ArrayList<>();
        doThrow(new IOException("Error")).when(mockMapper).writeValue(any(File.class), any(ArrayList.class));

        boolean result = jsonConnector.saveListings(listings);

        assertFalse(result);
        verify(mockMapper, times(1)).writeValue(any(File.class), any(ArrayList.class));
    }
}
