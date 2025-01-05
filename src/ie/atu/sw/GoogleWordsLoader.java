package ie.atu.sw;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The GoogleWordsLoader class is responsible for loading the Google 1000 words from a specified
 * file into a concurrent skip list set. It extends FileProcessor for its file processing.
 */
public class GoogleWordsLoader extends FileProcessor {
    private final ConcurrentSkipListSet<String> google1000Set = new ConcurrentSkipListSet<>();

    /**
     * Processes a single line of the Google 1000 words file.
     * Each line is expected to contain a single word which is trimmed and converted to lowercase.
     *
     * <p><b>Time Complexity:</b> O(1), as it involves a single insertion into the set.</p>
     *
     * @param line The line of text to process
     * @throws Exception if an error occurs during processing
     */
    @Override
    void process(String line) throws Exception {
        google1000Set.add(line.trim().toLowerCase());
    }

    /**
     * Retrieves the loaded Google 1000 words.
     *
     * <p><b>Time Complexity:</b> O(1), as it simply returns the reference to the set.</p>
     *
     * @return a ConcurrentSkipListSet containing all the loaded Google 1000 words
     */
    public ConcurrentSkipListSet<String> getGoogle1000Set() {
        return google1000Set;
    }

    /**
     * Processes a file to load Google 1000 words.
     * This method uses the loadAndProcessLines method from the FileProcessor class
     * to read and process each line of the input file.
     *
     * <p><b>Time Complexity:</b> O(n), where n is the number of lines in the file.</p>
     *
     * @param textFile   the path to the input file containing Google 1000 words
     * @param outputFile the path to the output file (not used in this implementation)
     * @throws Throwable if any other error occurs during processing
     */
    @Override
    void processFile(String textFile, String outputFile) throws Throwable {
        loadAndProcessLines(textFile);
    }
}