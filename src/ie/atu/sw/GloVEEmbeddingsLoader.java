package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The GloVEEmbeddingsLoader class is responsible for loading GloVe word embeddings
 * from a specified file into a concurrent hash map. It extends FileProcessor for
 * its file processing.
 */
public class GloVEEmbeddingsLoader extends FileProcessor {
    private final ConcurrentHashMap<String, double[]> embeddingsMap = new ConcurrentHashMap<>();

    /**
     * Processes a single line of the GloVe embeddings file.
     * Each line is expected to have a word followed by its embedding vector values, separated by commas.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of vector values in the line.</p>
     *
     * @param line The line of text to process
     * @throws Exception if an error occurs during processing
     */
    @Override
    void process(String line) throws Exception {
        String[] parts = line.split(",");
        String word = parts[0];
        double[] vector = new double[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            vector[i - 1] = Double.parseDouble(parts[i]);
        }
        embeddingsMap.put(word, vector);
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
     * Processes a file to load GloVe embeddings.
     * This method uses the loadAndProcessLines method from the FileProcessor class
     * to read and process each line of the input file.
     *
     * <p><b>Time Complexity:</b> O(n * m), where n is the number of lines in the file and m is the average
     * number of vector values per line.</p>
     *
     * @param textFile   the path to the input file containing GloVe embeddings
     * @param outputFile the path to the output file (not used in this implementation)
     * @throws Throwable if any other error occurs during processing
     */
    @Override
    void processFile(String textFile, String outputFile) throws Throwable {
        loadAndProcessLines(textFile);
    }
}