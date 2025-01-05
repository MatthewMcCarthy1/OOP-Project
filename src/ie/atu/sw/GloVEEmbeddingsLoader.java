package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The GloVEEmbeddingsLoader class is responsible for loading GloVe word embeddings
 * from a specified file into a concurrent hash map. It extends FileProcessor for
 * its file processing.
 */
public class GloVEEmbeddingsLoader extends FileProcessor {
    private final ConcurrentHashMap<String, double[]> embeddingsMap = new ConcurrentHashMap<>();

    @Override
    protected void process(String line) throws Exception {
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
     * Processes a file to load GloVE embeddings.
     *
     * @param textFile  the path to the input file
     * @param outputFile the path to the output file (not used in this implementation)
     * @throws IOException if an I/O error occurs during processing
     * @throws Throwable   if any other error occurs during processing
     */
    @Override
    public void processFile(String textFile, String outputFile) throws Throwable {
        load(textFile);
    }
}