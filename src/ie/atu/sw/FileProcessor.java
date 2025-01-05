package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

//Reference: Moodle -> oop-VirtualThreadLabs -> StructuredFileParser.java

/**
 * The FileProcessor class provides a method for writing to files and loading data.
 */
public abstract class FileProcessor {
    /**
     * Writes a List of Strings to a specified file using virtual threads.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines to be written.</p>
     *
     * @param filePath the path to the file where lines will be written
     * @param lines    a List of Strings to be written to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void writeFileLines(String filePath, List<String> lines) throws IOException {
        System.out.println("Writing to file: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
             var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (String line : lines) {
                scope.fork(() -> {
                    try {
                        writer.write(line);
                        writer.newLine();
                    } catch (IOException e) {
                        System.err.println("IO Exception while writing to file: " + filePath);
                        throw e;
                    }
                    return null;
                });
            }
            // Wait for all threads to complete and handle exceptions
            scope.join();
            scope.throwIfFailed();
        } catch (IOException e) {
            System.err.println("IO Exception while writing to file: " + filePath);
            throw e;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error during file processing: " + e.getMessage());
        }
    }

    /**
     * Abstract method to be implemented by subclasses for specific file processing logic.
     *
     * @param textFile   the path to the file to be processed
     * @param outputFile the path to the output file
     * @throws IOException if an I/O error occurs during processing
     * @throws Throwable   if any other error occurs during processing
     */
    abstract void processFile(String textFile, String outputFile) throws Throwable;

    /**
     * Loads data from a specified file and processes each line using virtual threads
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param filePath the path to the file to be loaded
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void loadAndProcessLines(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            String line;
            // Process each line in a separate virtual thread
            while ((line = reader.readLine()) != null) {
                String currentLine = line;
                scope.fork(() -> {
                    try {
                        process(currentLine); // Process the line
                    } catch (Exception e) {
                        System.err.println("Failed to process line: " + currentLine + ". Error: " + e.getMessage());
                    }
                    return null;
                });
            }
            // Wait for all threads to complete and handle exceptions
            scope.join();
            scope.throwIfFailed();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error during file processing: " + e.getMessage());
        }
    }

    /**
     * Abstract method to be implemented by subclasses to process a single line of the file.
     *
     * @param line the line to be processed
     * @throws Exception if an error occurs during processing
     */
    abstract void process(String line) throws Exception;
}