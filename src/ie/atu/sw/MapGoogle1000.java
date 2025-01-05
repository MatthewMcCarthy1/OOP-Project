package ie.atu.sw;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The MapGoogle1000 class manages word embeddings and similarity calculations
 * for text simplification using the Google 1000 most common words.
 */
public class MapGoogle1000 {
    private final ConcurrentSkipListSet<String> google1000Set;
    private final ConcurrentHashMap<String, double[]> embeddingsMap;
    private final ConcurrentHashMap<String, double[]> google1000Embeddings;
    private final SimilarityCalculator similarityCalculator;

    /**
     * Constructs a MapGoogle1000 object with the given Google 1000 set and embeddings map.
     *
     * <p><b>Time Complexity:</b> O(1), this constructor performs constant time operations</p>
     *
     * @param google1000Set The set of Google 1000 most common words
     * @param embeddingsMap The map of word embeddings
     */
    public MapGoogle1000(ConcurrentSkipListSet<String> google1000Set, ConcurrentHashMap<String, double[]> embeddingsMap) {
        this.google1000Set = google1000Set;
        this.embeddingsMap = embeddingsMap;
        this.google1000Embeddings = new ConcurrentHashMap<>();
        this.similarityCalculator = new SimilarityCalculator();
    }

    /**
     * Initializes the Google 1000 embeddings map with embeddings for words in the Google 1000 set.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of words in google1000Set, this method
     * iterates through each word in google1000Set once</p>
     */
    public synchronized void initializeGoogle1000Embeddings() {
        for (String word : google1000Set) {
            double[] embedding = embeddingsMap.get(word);
            if (embedding != null) {
                google1000Embeddings.put(word, embedding);
            }
        }
    }

    /**
     * Finds the most similar word from the Google 1000 set for a given word.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of words in google1000Embeddings, this method
     * iterates through each word in google1000Embeddings to calculate similarity</p>
     *
     * @param word The word to find a similar word for
     * @return The most similar word from the Google 1000 set, or the original word if not found
     */
    public synchronized String findMostSimilarWord(String word) {
        double[] wordVector = embeddingsMap.get(word);
        if (wordVector == null) {
            return word; // If the word is not in the embeddings map, return the word itself
        }

        String mostSimilarWord = word;
        double highestSimilarity = -1.0;

        for (var entry : google1000Embeddings.entrySet()) {
            double[] googleWordVector = entry.getValue();
            double similarity = similarityCalculator.calculate(wordVector, googleWordVector);

            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                mostSimilarWord = entry.getKey();
            }
        }

        return mostSimilarWord;
    }

    /**
     * Processes a word by either returning it if it's in the Google 1000 set,
     * finding a similar word if it has an embedding, or returning the original word.
     *
     * <p><b>Time Complexity:</b> O(1) for best case(word is in google1000Set), O(m) for worst case
     * (findMostSimilarWord is called), where m is the number of words in google1000Embeddings</p>
     *
     * @param word The word to process
     * @return The processed word
     */
    public synchronized String processWord(String word) {
        if (google1000Set.contains(word)) {
            return word;
        } else if (embeddingsMap.containsKey(word)) {
            return findMostSimilarWord(word);
        } else {
            return word;
        }
    }
}