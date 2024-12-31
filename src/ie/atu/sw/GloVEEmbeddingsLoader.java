package ie.atu.sw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.StructuredTaskScope;

//Reference: Moodle -> oop-VirtualThreadLabs -> StructuredFileParser.java

/**
 * The GloVEEmbeddingsLoader class is responsible for loading GloVe word embeddings
 * from a specified file into a concurrent hash map. It extends FileProcessor for
 * its file processing.
 */
public class GloVEEmbeddingsLoader extends FileProcessor {
    private final ConcurrentHashMap<String, double[]> embeddingsMap = new ConcurrentHashMap<>();

    /**
     * Loads word embeddings from a specified GloVe embeddings file using virtual threads.
     *
     * <p>Each line in the file is expected to contain a word followed by its corresponding
     * vector values, separated by commas. The word is stored as a key in the map,
     * and the vector is stored as a double array.</p>
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param embeddingsFile the path to the GloVe embeddings file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws Throwable   if an error occurs during concurrent task execution
     */
    public void loadWordEmbeddings(String embeddingsFile) throws Throwable {
        System.out.println("Loading word embeddings from: " + embeddingsFile);

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Files.lines(Paths.get(embeddingsFile)).forEach(line -> {
                scope.fork(() -> {
                    //split the line into parts
                    String[] parts = line.split(",");
                    String word = parts[0];
                    //convert the remaining parts into a vector
                    double[] vector = new double[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        vector[i - 1] = Double.parseDouble(parts[i]);
                    }
                    //store the word and its vector in the map
                    embeddingsMap.put(word, vector);
                    return null;
                });
            });
            //wait for all tasks to complete
            scope.join();
            //if a task failed, throw exception
            scope.throwIfFailed(e -> e);

            System.out.println("Successfully loaded word embeddings.");
        } catch (IOException e) {
            System.err.println("IO Exception while reading the embeddings file: " + embeddingsFile);
            throw e;
        } catch (Throwable e) {
            System.err.println("Error during concurrent task execution while loading embeddings file: " + embeddingsFile);
            throw e;
        }
    }

    /**
     * Retrieves the loaded word embeddings.
     *
     * <p><b>Time Complexity:</b> O(1), as it simply returns the reference to the map.</p>
     *
     * @return a ConcurrentHashMap containing words as keys and their corresponding vector
     * representations as values
     */
    public ConcurrentHashMap<String, double[]> getEmbeddings() {
        return embeddingsMap;
    }

    /**
     * Processes a file to load GloVE embeddings.
     *
     * @param inputFilePath  the path to the input file
     * @param outputFilePath the path to the output file (not used in this implementation)
     * @throws IOException if an I/O error occurs during processing
     * @throws Throwable   if any other error occurs during processing
     */
    @Override
    public void processFile(String inputFilePath, String outputFilePath) throws Throwable {
        loadWordEmbeddings(inputFilePath);
    }
}