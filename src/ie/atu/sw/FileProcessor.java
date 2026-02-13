package ie.atu.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

/**
 * The FileProcessor class provides methods for reading and writing files.
 */
public abstract class FileProcessor {
    /**
     * Writes a List of Strings to a specified file sequentially.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines to be written.</p>
     *
     * @param filePath the path to the file where lines will be written
     * @param lines    a List of Strings to be written to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void writeFileLines(String filePath, List<String> lines) throws IOException {
        System.out.println("Writing to file: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    /**
     * Abstract method to be implemented by subclasses for specific file processing logic.
     *
     * @param textFile   the path to the file to be processed
     * @param outputFile the path to the output file
     * @throws Throwable   if any error occurs during processing
     */
    abstract void processFile(String textFile, String outputFile) throws Throwable;

    /**
     * Loads data from a specified file and processes each line using virtual threads.
     * Note: Order is not guaranteed for the process() method calls.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param filePath the path to the file to be loaded
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void loadAndProcessLines(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             var scope = StructuredTaskScope.open()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String currentLine = line;
                scope.fork(() -> {
                    try {
                        process(currentLine);
                    } catch (Exception e) {
                        System.err.println("Failed to process line. Error: " + e.getMessage());
                    }
                    return null;
                });
            }
            scope.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Error during concurrent line processing", e);
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
