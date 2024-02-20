package com.mse.input;

import com.mse.entities.ApplicationConfig;
import com.mse.enums.WebsiteID;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Command line interface for configuring the crawler application
 */
@Getter
public class CommandLineInterface {
    public static String[] THREAD_OPTIONS = {"1", "2", "4"};
    private final ApplicationConfig config;

    /**
     * Constructs the CommandLineInterface object and prompts the user to enter their desired preferences
     * For all 3 input prompts, the execution would not continue until a valid value is provided
     */
    public CommandLineInterface() {
        System.out.println("-------- WEB CRAWLER --------");
        System.out.println();

        Scanner inputScanner = new Scanner(System.in);
        WebsiteID website = null;

        while (Objects.isNull(website)) {
            System.out.println("Available websites to crawl:");
            System.out.println("1. Jobs.bg");
            System.out.println("2. Rabota.bg");
            System.out.println("3. Zaplata.bg");
            System.out.println("Your choice: ");

            String websiteInput = inputScanner.nextLine();

            switch (websiteInput) {
                case "1":
                    website = WebsiteID.JOBS;
                    break;
                case "2":
                    website = WebsiteID.RABOTA;
                    break;
                case "3":
                    website = WebsiteID.ZAPLATA;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }

        Integer threadCount = null;
        while (Objects.isNull(threadCount)) {
            System.out.println("Select thread count for action (Possible options 1, 2, 4):");

            String threadInput = inputScanner.nextLine();

            if (Arrays.asList(THREAD_OPTIONS).contains(threadInput)) {
                threadCount = Integer.parseInt(threadInput);
            } else {
                System.out.println("Invalid input!");
            }
        }

        Integer pageCount = null;
        while (Objects.isNull(pageCount)) {
            System.out.println("Enter number of listing pages to parse:");

            String pageInput = inputScanner.nextLine();

            try{
                pageCount = Integer.parseInt(pageInput);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input!");
            }
        }

        this.config = new ApplicationConfig(threadCount, pageCount, website);
    }
}
