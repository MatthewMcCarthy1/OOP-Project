package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * The FileProcessor class provides a method for writing to files.
 * It requires subclasses to implement specific file processing logic.
 */
public abstract class FileProcessor {
    /**
     * Writes a List of Strings to a specified file.
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
        } catch (IOException e) {
            System.err.println("IO Exception while writing to file: " + filePath);
            throw e;
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
    public abstract void processFile(String textFile, String outputFile) throws Throwable;
}