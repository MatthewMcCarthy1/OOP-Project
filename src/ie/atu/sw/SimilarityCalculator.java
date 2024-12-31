package ie.atu.sw;

//Reference: https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-similarity-of-two-vectors

/**
 * The SimilarityCalculator class provides methods to calculate the cosine similarity between two vectors.
 *
 * <p>Cosine similarity is a measure of similarity between two non-zero vectors of an inner product space.
 * It is defined to equal the cosine of the angle between them, which is also the same as the inner product
 * of the same vectors normalized to both have length 1.</p>
 */
public class SimilarityCalculator {
    /**
     * Calculates the cosine similarity between two vectors.
     *
     * <p>The method computes the dot product of the two vectors and divides it by the product
     * of their Euclidean norms. If either vector is null or if the vectors have different lengths,
     * the method returns 0.</p>
     *
     * <p><b>Time Complexity:</b> O(n), where n is the length of the vectors.</p>
     *
     * @param vectorA the first vector
     * @param vectorB the second vector
     * @return the cosine similarity between vectorA and vectorB, or 0 if the vectors are invalid
     */
    public double calculate(double[] vectorA, double[] vectorB) {
        if (vectorA == null || vectorB == null || vectorA.length != vectorB.length) {
            return 0;
        }

        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            norm1 += vectorA[i] * vectorA[i];
            norm2 += vectorB[i] * vectorB[i];
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
