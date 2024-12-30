package ie.atu.sw;

import java.io.*;
import java.util.*;
/**
 * The FileProcessor class provides methods for reading from and writing to files.
 * It handles basic file I/O operations, specifically for reading and writing lists of strings.
 */
public abstract class FileProcessor {
    /**
     * Reads all lines from a specified file and returns them as a List of Strings.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param filePath the path to the file to be read
     * @return a List of Strings containing all lines from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<String> readFileLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    /**
     * Writes a List of Strings to a specified file.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines to be written.</p>
     *
     * @param filePath the path to the file where lines will be written
     * @param lines a List of Strings to be written to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void writeFileLines(String filePath, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}