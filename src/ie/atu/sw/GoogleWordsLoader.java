package ie.atu.sw;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The GoogleWordsLoader class is responsible for loading the Google 1000 words from a specified
 * file into a concurrent skip list set. It extends FileProcessor for its file processing.
 */
public class GoogleWordsLoader extends FileProcessor {
    private final ConcurrentSkipListSet<String> google1000Set = new ConcurrentSkipListSet<>();

    @Override
    protected void process(String line) throws Exception {
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